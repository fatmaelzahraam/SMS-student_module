package com.ntg.sms.Mapper;

import com.ntg.sms.Entities.Dtos.Request.MarksTypeRequest;
import com.ntg.sms.Entities.Dtos.Response.MarksTypeResponse;
import com.ntg.sms.Entities.MarksType;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface MarksTypeMapper {

    @Mapping(source = "typeName", target = "type")
    MarksTypeResponse toResponse(MarksType marksType);

    @Mapping(source = "id",   target = "id")
    @Mapping(source = "type", target = "typeName")
    MarksType toEntity(MarksTypeRequest request);

    @Mapping(source = "type", target = "typeName")
    @Mapping(target = "id",   ignore = true)
    void updateEntity(@MappingTarget MarksType marksType, MarksTypeRequest request);
}