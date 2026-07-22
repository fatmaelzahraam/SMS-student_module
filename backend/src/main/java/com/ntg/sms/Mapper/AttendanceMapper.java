package com.ntg.sms.Mapper;

import com.ntg.sms.Entities.Attendance;
import com.ntg.sms.Entities.Dtos.Response.AttendaceResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AttendanceMapper {

    @Mapping(source = "student.id",   target = "studentId")
    @Mapping(source = "session.id",   target = "sessionId")

    // Student full name
    @Mapping(
            target = "studentName",
            expression = "java(attendance.getStudent().getUser().getFirstName() + \" \" + attendance.getStudent().getUser().getLastName())"
    )

    // Course name via session
    @Mapping(
            target = "courseName",
            expression = "java(attendance.getSession() != null ? attendance.getSession().getCourse().getCourseName() : null)"
    )

    // Teacher name via session → course → teacher → user
    @Mapping(
            target = "teacherName",
            expression = "java(attendance.getSession() != null ? attendance.getSession().getCourse().getTeacher().getUser().getFirstName() + \" \" + attendance.getSession().getCourse().getTeacher().getUser().getLastName() : null)"
    )

    AttendaceResponse toResponse(Attendance attendance);
}