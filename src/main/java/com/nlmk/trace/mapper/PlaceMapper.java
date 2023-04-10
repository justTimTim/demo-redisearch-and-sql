package com.nlmk.trace.mapper;

import com.nlmk.trace.model.document.PlaceDoc;
import com.nlmk.trace.model.entity.Place;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PlaceMapper {

    Place toEntity(PlaceDoc doc);

    PlaceDoc toRedis(Place place);
}
