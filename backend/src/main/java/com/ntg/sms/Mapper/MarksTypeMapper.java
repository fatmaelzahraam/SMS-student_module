package com.ntg.sms.Mapper;


import com.ntg.sms.Entities.Dtos.Request.MarksTypeRequest;
import com.ntg.sms.Entities.Dtos.Response.MarksTypeResponse;
import com.ntg.sms.Entities.MarksType;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface MarksTypeMapper {

    MarksTypeResponse toResponse(MarksType marksType);

    @Mapping(source = "id",   target = "id")
    @Mapping(source = "type", target = "type")
    MarksType toEntity(MarksTypeRequest request);

    @Mapping(source = "type", target = "type")
    @Mapping(target = "id",   ignore = true)
    void updateEntity(@MappingTarget MarksType marksType, MarksTypeRequest request);
}
