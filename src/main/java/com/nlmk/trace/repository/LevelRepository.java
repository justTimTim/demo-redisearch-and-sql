package com.nlmk.trace.repository;

import com.nlmk.trace.model.entity.Level;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LevelRepository extends JpaRepository<Level, UUID> {
}
