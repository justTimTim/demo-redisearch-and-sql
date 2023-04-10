package com.nlmk.trace.service;

import com.nlmk.trace.model.entity.Downtime;
import com.nlmk.trace.repository.DowntimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class SaveHelper {
    private final DowntimeRepository repository;

    @Transactional
    public void saveAll(Iterable<Downtime> all) {
        repository.saveAll(all);
    }

}
