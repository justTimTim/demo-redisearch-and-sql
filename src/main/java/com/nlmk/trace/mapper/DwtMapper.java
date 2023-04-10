package com.nlmk.trace.mapper;

import com.nlmk.trace.model.document.DowntimeDoc;
import com.nlmk.trace.model.entity.Downtime;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DwtMapper {

    Downtime toEntity(DowntimeDoc doc);

    DowntimeDoc toRedis(Downtime downtime);
}
