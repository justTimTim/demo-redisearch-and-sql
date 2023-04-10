package com.nlmk.trace.repository;

import com.nlmk.trace.model.entity.Fixer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FixerRepository extends JpaRepository<Fixer, UUID> {
}
