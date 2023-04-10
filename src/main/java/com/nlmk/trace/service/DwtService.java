package com.nlmk.trace.service;

import com.github.f4b6a3.ulid.UlidCreator;
import com.nlmk.trace.mapper.DwtMapper;
import com.nlmk.trace.model.document.DowntimeDoc;
import com.nlmk.trace.model.entity.*;
import com.nlmk.trace.repository.*;
import com.nlmk.trace.repository.redis.DowntimeRedisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class DwtService {

    private final DowntimeRepository downtimeRepository;
    private final SaveHelper saveHelper;
    private final CauseRepository causeRepository;
    private final PlaceRepository placeRepository;
    private final FixerRepository fixerRepository;
    private final LevelRepository levelRepository;
    private final DowntimeRedisRepository downtimeRedisRepository;
    private final DwtMapper mapper;
    private final Random random = new Random();

    public void createDwt() {
        var pageRequest = PageRequest.of(0, 100);
        var areas = placeRepository.findAllByLevelName("Level 3", pageRequest).toList();
        var causes = causeRepository.findAll();
        var fixers = fixerRepository.findAll();

        int size = 10;
        long time = 0;
        long time2 = 0;
        long[] arrSql = new long[size];
        long[] arrRed = new long[size];

        for (int k = 0; k < size; k++) {

            for (int i = 0; i < 10; i++) {
                List<Downtime> list = new LinkedList<>();
                List<DowntimeDoc> listDoc = new LinkedList<>();
                for (int j = 0; j < 1000; j++) {
                    Downtime downtime = buildDwt(areas, causes, fixers, j);
                    list.add(downtime);
                    listDoc.add(mapper.toRedis(downtime));
                }
// ================= to postgres
                long l = System.nanoTime();
                saveHelper.saveAll(list);
                long st = Duration.ofNanos(System.nanoTime() - l).toMillis();
                time += st;
                arrSql[k] = st;

// ================= to redis
                long l2 = System.nanoTime();
                downtimeRedisRepository.saveAll(listDoc);
                long st2 = Duration.ofNanos(System.nanoTime() - l2).toMillis();
                time2 += st2;
                arrRed[k] = st2;
            }

        }

        log.info("sql {}", arrSql);
        log.info("redis {}", arrRed);
        log.info("recorded in {} ms.", time / 10);
        log.info("recorded to Redis in {} ms.", time2 / 10);

    }


    private Downtime buildDwt(List<Place> areas, List<Cause> causes, List<Fixer> fixers, int j) {

        LocalDateTime time = LocalDateTime.of(2020, 1, 1, 0, 0, 0);
        int shiftDate = random
                .ints(1, 300)
                .findFirst()
                .orElse(2);
        LocalDateTime beginDate = time.plusDays(j).plusHours(shiftDate);
        return Downtime.builder()
                .id(UlidCreator.getMonotonicUlid().toString())
                .beginDate(beginDate)
                .endDate(beginDate.plusMinutes(shiftDate))
                .area(areas.get(random
                        .ints(1, 100)
                        .findFirst()
                        .orElse(0)))
                .cause(causes.get(random
                        .ints(1, 4999)
                        .findFirst()
                        .orElse(0)))
                .description("description " + j)
                .shift(j % 2 == 0 ? "1" : "2")
                .brigade(j % 2 == 0 ? "2" : "1")
                .fixer(fixers.get(random.ints(0, 1999)
                        .findFirst()
                        .orElse(0)))
                .responsible(UlidCreator.getMonotonicUlid().toString())
                .created(beginDate)
                .createdBy(UlidCreator.getMonotonicUlid().toString())
                .updated(beginDate)
                .updateBy(UlidCreator.getMonotonicUlid().toString())
                .isActive(true)
                .build();
    }


    public void createPlace() {
        List<String> country = List.of("Russia", "Italy", "USA");
        List<Level> levels = levelRepository.findAll();

        int n = 1;
        for (int i = 0; i < 100; i++) {
            List<Place> places = new LinkedList<>();
            for (int j = 0; j < 200; j++) {
                places.add(
                        Place.builder()
                                .id(UlidCreator.getMonotonicUlid().toString())
                                .name("place " + n++)
                                .country(country.get(random
                                        .ints(0, 3)
                                        .findFirst()
                                        .orElse(0)))
                                .level(levels.get(random
                                        .ints(0, levels.size())
                                        .findFirst()
                                        .orElse(0))
                                )
                                .build()

                );
            }
            placeRepository.saveAll(places);
        }

    }

    public void createLevel() {
        List<Level> levels = new LinkedList<>();
        for (int i = 1; i < 4; i++) {
            levels.add(
                    Level.builder()
                            .id(UlidCreator.getMonotonicUlid().toString())
                            .name("Level " + i)
                            .build()
            );
        }
        levelRepository.saveAll(levels);
    }

    public void createCause() {
        List<Cause> causes = new LinkedList<>();
        for (int i = 1; i < 5000; i++) {
            causes.add(
                    Cause.builder()
                            .id(UlidCreator.getMonotonicUlid().toString())
                            .name("Name " + i)
                            .description("description " + i)
                            .build()
            );
        }
        causeRepository.saveAll(causes);
    }

    public void createFixer() {
        List<Fixer> causes = new LinkedList<>();
        for (int i = 1; i < 2000; i++) {
            causes.add(
                    Fixer.builder()
                            .id(UlidCreator.getMonotonicUlid().toString())
                            .name("Name " + i)
                            .build()
            );
        }
        fixerRepository.saveAll(causes);
    }

}
