package com.nlmk.trace.repository;

import com.nlmk.trace.model.entity.Place;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface PlaceRepository extends JpaRepository<Place, UUID> {

    @Query("SELECT p FROM Place p WHERE p.level.name = ?1")
    Page<Place> findAllByLevelName(String level, Pageable pageable);
}
