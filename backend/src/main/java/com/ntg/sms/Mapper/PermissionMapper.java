package com.ntg.sms.Mapper;

import com.ntg.sms.Entities.Dtos.Request.PermissionRequest;
import com.ntg.sms.Entities.Dtos.Response.PermissionResponse;
import com.ntg.sms.Entities.Permission;
import com.ntg.sms.Entities.Student;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PermissionMapper {

    @Mapping(source = "student.id",  target = "studentId")
    @Mapping(target = "studentName", expression = "java(permission.getStudent().getUser().getFirstName() + \" \" + permission.getStudent().getUser().getLastName())")
    PermissionResponse toResponse(Permission permission);

    @Mapping(source = "request.reason", target = "reason")
    @Mapping(source = "request.notes",  target = "notes")
    @Mapping(source = "request.date",   target = "date")
    @Mapping(source = "student",        target = "student")
    @Mapping(target = "id",             ignore = true)
    Permission toEntity(PermissionRequest request, Student student);

    @Mapping(source = "request.reason", target = "reason")
    @Mapping(source = "request.notes",  target = "notes")
    @Mapping(source = "request.date",   target = "date")
    @Mapping(source = "student",        target = "student")
    @Mapping(target = "id",             ignore = true)
    void updateEntity(@MappingTarget Permission permission, PermissionRequest request, Student student);
}