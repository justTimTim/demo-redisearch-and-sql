package com.nlmk.trace.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Service
@Entity
@Table(schema = "test", name = "place")
public class Place {

    @Id
    private String id;
    private String name;
    private String country;
    @ManyToOne(fetch = FetchType.EAGER)
    private Level level;
}
