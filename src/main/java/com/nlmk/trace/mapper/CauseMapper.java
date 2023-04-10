package com.nlmk.trace.mapper;

import com.nlmk.trace.model.document.CauseDoc;
import com.nlmk.trace.model.entity.Cause;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CauseMapper {

    Cause toEntity(CauseDoc doc);

    CauseDoc toRedis(Cause cause);
}
