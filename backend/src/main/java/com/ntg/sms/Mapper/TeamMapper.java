package com.ntg.sms.Mapper;

import com.ntg.sms.Entities.Dtos.Response.TeamResponse;
import com.ntg.sms.Entities.Team;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring", imports = Collectors.class)
public interface TeamMapper {

    @Mapping(source = "project.id", target = "projectId")
    @Mapping(source = "project.name", target = "projectName")
    @Mapping(target = "studentIds",
            expression = "java(team.getStudents().stream().map(student -> student.getId()).collect(Collectors.toSet()))")
    TeamResponse toResponse(Team team);

}
