package com.ntg.sms.Mapper;


import com.ntg.sms.Entities.Class;
import com.ntg.sms.Entities.Dtos.Request.ClassRequest;
import com.ntg.sms.Entities.Dtos.Response.ClassResponse;
import com.ntg.sms.Entities.Grade;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ClassMapper {

    @Mapping(source = "grade.id",   target = "gradeId")
    @Mapping(source = "grade.name", target = "gradeName")
    ClassResponse toResponse(Class classEntity);

    @Mapping(source = "grade",            target = "grade")
    @Mapping(source = "request.name",     target = "name")
    @Mapping(source = "request.capacity", target = "capacity")
    @Mapping(target = "id",               ignore = true)
    Class toEntity(ClassRequest request, Grade grade);

    @Mapping(source = "grade",            target = "grade")
    @Mapping(source = "request.name",     target = "name")
    @Mapping(source = "request.capacity", target = "capacity")
    @Mapping(target = "id",               ignore = true)
    void updateEntity(@MappingTarget Class classEntity, ClassRequest request, Grade grade);
}