package com.nlmk.trace.model.document;

import com.redis.om.spring.annotations.Document;
import com.redis.om.spring.annotations.Indexed;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document
public class DowntimeDoc {

    @Id
    @Indexed
    private String id;
    @Indexed(sortable = true)
    private LocalDateTime beginDate;
    @Indexed(sortable = true)
    private LocalDateTime endDate;
    @Indexed(sortable = true)
    private PlaceDoc area;
    @Indexed
    private CauseDoc cause;
    private String description;
    private String shift;
    private String brigade;
    @Indexed
    private FixerDoc fixer;
    @Indexed
    private String responsible;
    private LocalDateTime created;
    private String createdBy;
    private LocalDateTime updated;
    private String updateBy;
    private Boolean isActive;

}
