package com.nlmk.trace.model.document;

import com.redis.om.spring.annotations.Indexed;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class PlaceDoc {

    @Indexed
    private String id;
    @Indexed
    private String name;
    private String country;
    private LevelDoc level;
}
