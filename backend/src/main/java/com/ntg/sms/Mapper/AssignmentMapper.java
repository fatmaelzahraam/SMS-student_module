package com.ntg.sms.Mapper;

import com.ntg.sms.Entities.Assignment;
import com.ntg.sms.Entities.Dtos.Response.AssignmentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AssignmentMapper {

    @Mapping(target = "courseIds", expression =
            "java(assignment.getCourses().stream().map(course -> course.getId()).collect(java.util.stream.Collectors.toSet()))")
    AssignmentResponse toResponse(Assignment assignment);

}