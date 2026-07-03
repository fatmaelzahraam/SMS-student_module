package com.ntg.sms.Mapper;

import com.ntg.sms.Entities.Project;
import com.ntg.sms.Entities.Dtos.Response.ProjectResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

    @Mapping(source = "course.id", target = "courseId")
    ProjectResponse toResponse(Project project);

}