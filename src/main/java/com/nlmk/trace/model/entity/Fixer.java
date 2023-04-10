package com.nlmk.trace.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Table(schema = "test", name = "fixer")
public class Fixer {

    @Id
    private String id;
    private String name;
}
