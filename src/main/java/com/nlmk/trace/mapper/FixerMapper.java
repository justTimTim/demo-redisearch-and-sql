package com.nlmk.trace.mapper;

import com.nlmk.trace.model.document.FixerDoc;
import com.nlmk.trace.model.entity.Fixer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FixerMapper {

    Fixer toEntity(FixerDoc doc);

    FixerDoc toRedis(Fixer fixer);
}
