package com.ntg.sms.Mapper;

import com.ntg.sms.Entities.Dtos.Response.ViolationResponse;
import com.ntg.sms.Entities.Violation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ViolationMapper {

    @Mapping(source = "id", target = "violationId")
    ViolationResponse toResponse(Violation violation);

}
