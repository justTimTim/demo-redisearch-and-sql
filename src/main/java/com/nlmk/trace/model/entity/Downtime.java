package com.nlmk.trace.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Service
@Entity
@Table(schema = "test", name = "downtime")
public class Downtime {

    @Id
    private String id;
    private LocalDateTime beginDate;
    private LocalDateTime endDate;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "area")
    private Place area;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cause")
    private Cause cause;
    private String description;
    private String shift;
    private String brigade;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fixer")
    private Fixer fixer;
    private String responsible;
    private LocalDateTime created;
    private String createdBy;
    private LocalDateTime updated;
    private String updateBy;
    private Boolean isActive;
}
