package com.ntg.sms.Mapper;

import com.ntg.sms.Entities.Dtos.Response.SessionResponse;
import com.ntg.sms.Entities.Session;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SessionMapper {

    @Mapping(source = "classField.id", target = "classId")
    @Mapping(source = "classField.name", target = "className")
    @Mapping(source = "course.id", target = "courseId")
    SessionResponse toResponse(Session session);

}
