package com.ntg.sms.Mapper;

import com.ntg.sms.Entities.Attendance;
import com.ntg.sms.Entities.Dtos.Response.AttendaceResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AttendanceMapper {

    @Mapping(source = "student.id", target = "studentId")
    @Mapping(source = "session.id", target = "sessionId")
    @Mapping(source = "attendance.id", target = "id")
    AttendaceResponse toResponse(Attendance attendance);

}