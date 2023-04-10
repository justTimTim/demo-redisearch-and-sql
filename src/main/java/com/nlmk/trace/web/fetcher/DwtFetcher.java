package com.nlmk.trace.web.fetcher;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import com.nlmk.trace.model.entity.Downtime;
import com.nlmk.trace.model.request.Filter;
import com.nlmk.trace.model.response.TopCauses;
import com.nlmk.trace.service.DowntimeFethService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@DgsComponent
@RequiredArgsConstructor
public class DwtFetcher {

    private final DowntimeFethService downtimeFethService;

    @DgsQuery
    public Downtime findFirstByCauseSql(@InputArgument String causeId) {
        return downtimeFethService.findFirstByCauseSql(causeId);
    }

    @DgsQuery
    public Downtime findFirstByCauseRedis(@InputArgument String causeId) {
        return downtimeFethService.findFirstByCauseRedis(causeId);
    }

    @DgsQuery
    public List<Downtime> searchSql(@InputArgument Filter filter) {
        return downtimeFethService.searchDwtSql(filter);
    }

    @DgsQuery
    public List<Downtime> searchRedis(@InputArgument Filter filter) {
        return downtimeFethService.searchDwtRedis(filter);
    }

    @DgsQuery
    public List<Downtime> searchRedisStream(@InputArgument Filter filter) {
        return downtimeFethService.searchDwtStream(filter);
    }

    @DgsQuery
    public List<TopCauses> aggregateStream(Filter filter) {
        return downtimeFethService.topFiveCauses(filter);
    }

    @DgsQuery
    public List<TopCauses> aggregateSql(Filter filter) {
        return downtimeFethService.topFiveCausesSql(filter);
    }

    @DgsQuery
    public List<TopCauses> aggregateCustom(Filter filter) {
        return downtimeFethService.topFiveCausesCustom(filter);
    }
}
