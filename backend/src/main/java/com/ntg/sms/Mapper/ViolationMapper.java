package com.ntg.sms.Mapper;

import com.ntg.sms.Entities.Dtos.Response.ViolationResponse;
import com.ntg.sms.Entities.Violation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ViolationMapper {

    @Mapping(source = "student.id", target = "studentId")
    ViolationResponse toResponse(Violation violation);

}
