package com.ntg.sms.Mapper;

import com.ntg.sms.Entities.Dtos.Response.SessionResponse;
import com.ntg.sms.Entities.Session;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SessionMapper {

    @Mapping(source = "classField.name", target = "className")
    @Mapping(source = "course.courseName", target = "courseName")
    @Mapping(source = "updatedAt", target = "examDate")
    @Mapping(
            target = "teacherName",
            expression = "java(session.getCourse().getTeacher().getUser().getFirstName() + \" \" + session.getCourse().getTeacher().getUser().getLastName())"
    )
    SessionResponse toResponse(Session session);

}