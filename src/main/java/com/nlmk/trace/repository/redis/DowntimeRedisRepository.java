package com.nlmk.trace.repository.redis;

import com.nlmk.trace.model.document.DowntimeDoc;
import com.redis.om.spring.annotations.Query;
import com.redis.om.spring.repository.RedisDocumentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DowntimeRedisRepository extends RedisDocumentRepository<DowntimeDoc, String> {

    DowntimeDoc findTopByCause_IdOrderByBeginDateAsc(String causeId);

    @Query("(@area_id:{$areas} ) & (@beginDate:[$start $end] | @cause_id:{$causes})")
    Page<DowntimeDoc> findByParams(@Param("areas") List<String> areas,
                                   @Param("start") long start,
                                   @Param("end") long end,
                                   @Param("causes") List<String> causes, Pageable pageable);


}
