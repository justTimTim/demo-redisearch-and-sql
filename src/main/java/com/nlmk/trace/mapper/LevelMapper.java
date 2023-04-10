package com.nlmk.trace.mapper;

import com.nlmk.trace.model.document.LevelDoc;
import com.nlmk.trace.model.entity.Level;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LevelMapper {

    Level toEntity(LevelDoc doc);
    LevelDoc toRedis(Level level);
}
