package com.nlmk.trace.repository;

import com.nlmk.trace.model.entity.Cause;
import com.nlmk.trace.model.entity.Downtime;
import com.nlmk.trace.model.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface DowntimeRepository extends JpaRepository<Downtime, String> {

    Downtime findFirstByCauseIdOrderByBeginDate(String causeId);


    //FT.SEARCH com.nlmk.trace.model.document.DowntimeDocIdx (@area_id:{01GT29PCWSFA81TNJV2KZBYMWD | ...} )
    // & (@beginDate:[1632776400000 1632862800000] | @cause_id:{01GT29SSVXMFCSJPTJR1780ACZ | ...}) LIMIT 0 200
    @Query("SELECT d FROM Downtime d" +
            " JOIN FETCH d.area " +
            " JOIN FETCH d.cause" +
            " JOIN FETCH d.fixer" +
            " JOIN FETCH d.area.level " +
            " WHERE d.area IN ?1 AND (d.beginDate BETWEEN ?2 AND ?3 OR d.cause IN ?4) ")
    List<Downtime> findAllByParams(List<Place> workPlace, LocalDateTime start, LocalDateTime end, List<Cause> causes);

//FT.AGGREGATE com.nlmk.trace.model.document.DowntimeDocIdx @beginDate:[1625086800000 1627678800000]
// GROUPBY 1 @cause_name REDUCE COUNT 0 AS count SORTBY 4 @count DESC @cause_name ASC LIMIT 0 5 (or MAX 5)
    @Query(nativeQuery = true, value = "SELECT c.name, count(dwt.id) count_cause " +
            "FROM test.downtime dwt " +
            "         INNER JOIN test.cause c ON dwt.cause = c.id " +
            "WHERE begin_date BETWEEN ?1 AND ?2 " +
            "GROUP BY c.name " +
            "ORDER BY count_cause DESC, c.name " +
            "LIMIT ?3")
    List<Map<String, Object>> getTopCauses(LocalDateTime start, LocalDateTime end, Integer limit);
}
