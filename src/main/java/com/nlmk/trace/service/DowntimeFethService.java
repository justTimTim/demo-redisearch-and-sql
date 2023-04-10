package com.nlmk.trace.service;

import com.nlmk.trace.mapper.DwtMapper;
import com.nlmk.trace.model.document.DowntimeDoc;
import com.nlmk.trace.model.document.DowntimeDoc$;
import com.nlmk.trace.model.entity.Cause;
import com.nlmk.trace.model.entity.Downtime;
import com.nlmk.trace.model.entity.Place;
import com.nlmk.trace.model.request.Filter;
import com.nlmk.trace.model.response.TopCauses;
import com.nlmk.trace.repository.DowntimeRepository;
import com.nlmk.trace.repository.redis.DowntimeRedisRepository;
import com.redis.om.spring.annotations.ReducerFunction;
import com.redis.om.spring.client.RedisModulesClient;
import com.redis.om.spring.search.stream.EntityStream;
import com.redis.om.spring.tuple.Pair;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import redis.clients.jedis.search.aggr.AggregationBuilder;
import redis.clients.jedis.search.aggr.Reducers;
import redis.clients.jedis.search.aggr.SortedField;

import java.nio.charset.StandardCharsets;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DowntimeFethService {

    private final DowntimeRepository downtimeRepository;
    private final DowntimeRedisRepository downtimeRedisRepository;
    private final DwtMapper mapper;
    private final EntityStream entityStream;
    private final RedisModulesClient redisModulesClient;

    public Downtime findFirstByCauseSql(String causeId) {
        return downtimeRepository.findFirstByCauseIdOrderByBeginDate(causeId);
    }

    public Downtime findFirstByCauseRedis(String causeId) {
        return mapper.toEntity(downtimeRedisRepository.findTopByCause_IdOrderByBeginDateAsc(causeId));
    }

    public List<Downtime> searchDwtSql(Filter filter) {

        var workPlaces = filter.getWorkPlace().stream()
                .map(el -> Place.builder()
                        .id(el)
                        .build())
                .toList();

        var causes = filter.getCause().stream()
                .map(el -> Cause.builder()
                        .id(el)
                        .build())
                .toList();

        return downtimeRepository.findAllByParams(workPlaces, filter.getBeginDate().get(0),
                filter.getBeginDate().get(1), causes);
    }

    public List<Downtime> searchDwtRedis(Filter filter) {
        var workPlaces = filter.getWorkPlace();
        var causes = filter.getCause();

        Pageable pageable = PageRequest.of(0, 200);
        List<DowntimeDoc> allByParams =
                downtimeRedisRepository.findByParams(workPlaces,
                filter.getBeginDate().get(0).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
                filter.getBeginDate().get(1).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
                causes, pageable).toList();

        return allByParams.stream().map(mapper::toEntity).toList();
    }

    public List<Downtime> searchDwtStream(Filter filter) {
        String causes = "@cause_id:{"
                + String.join(" | ", filter.getCause())
                + "}";

        String between = "@beginDate:["
                + filter.getBeginDate().get(0).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
                + " "
                + filter.getBeginDate().get(1).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
                + "]";

        return entityStream
                .of(DowntimeDoc.class)
                .filter(DowntimeDoc$.AREA_ID.in(filter.getWorkPlace().toArray(String[]::new)))
                .filter(between + " | " + causes)
                .map(mapper::toEntity)
                .collect(Collectors.toList());
    }


    /*  ======AGGREGATION=======  */

    public List<TopCauses> topFiveCausesSql(Filter filter) {
        List<Map<String, Object>> count = downtimeRepository.getTopCauses(
                filter.getBeginDate().get(0),
                filter.getBeginDate().get(1),
                5
        );

        return count.stream()
                .map(el ->
                        new TopCauses(
                                el.get("name").toString(),
                                Long.valueOf(el.get("count_cause").toString()).intValue())
                ).toList();
    }

    public List<TopCauses> topFiveCauses(Filter filter) {
        List<Pair<String, Integer>> count =
                entityStream.of(DowntimeDoc.class)
                        .filter(DowntimeDoc$.BEGIN_DATE.between(
                                filter.getBeginDate().get(0),
                                filter.getBeginDate().get(1))
                        )
                        .groupBy(DowntimeDoc$.CAUSE_NAME)
                        .reduce(ReducerFunction.COUNT).as("count")
                        .sorted(Sort.Order.desc("@count"), Sort.Order.asc("@cause_name"))
                        .limit(0, 5)
                        .toList(String.class, Integer.class);

        return count.stream()
                .map(el -> new TopCauses(el.getFirst(), el.getSecond()))
                .toList();
    }

    public List<TopCauses> topFiveCausesCustom(Filter filter) {

        var search = redisModulesClient.clientForSearch();

        String query = "@beginDate:["
                + filter.getBeginDate().get(0).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
                + " "
                + filter.getBeginDate().get(1).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
                + "]";

        var aggregationBuilder = new AggregationBuilder(query);
        aggregationBuilder.groupBy("@cause_name", Reducers.count().setAlias("count"));
        aggregationBuilder.sortBy(SortedField.desc("@count"), SortedField.asc("@cause_name"));
        aggregationBuilder.limit(0, 5);

        return search.ftAggregate("com.nlmk.trace.model.document.DowntimeDocIdx", aggregationBuilder)
                .getResults().stream()
                .map(el ->
                        new TopCauses(
                                new String((byte[]) el.get("cause_name"), StandardCharsets.UTF_8),
                                Integer.valueOf(new String((byte[]) el.get("count"), StandardCharsets.UTF_8))
                        )
                ).toList();
    }

}