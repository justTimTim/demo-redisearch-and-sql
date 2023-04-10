package com.nlmk.trace.repository;

import com.nlmk.trace.model.entity.Cause;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CauseRepository extends JpaRepository<Cause, UUID> {
}
