package com.ntg.sms.Mapper;

import com.ntg.sms.Entities.Assignment;
import com.ntg.sms.Entities.Course;
import com.ntg.sms.Entities.Dtos.Response.CourseResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    @Mapping(source = "teacher.id",   target = "teacherId")
    @Mapping(target = "teacherName",  expression = "java(course.getTeacher().getUser().getFirstName() + \" \" + course.getTeacher().getUser().getLastName())")
    @Mapping(source = "term.id",      target = "termId")
    @Mapping(source = "term.term",    target = "termNumber")
    @Mapping(source = "term.year",    target = "termYear")
    @Mapping(source = "assignments",  target = "assignments")
    CourseResponse toResponse(Course course);

    @Mapping(source = "id",   target = "id")
    @Mapping(source = "name", target = "name")
    CourseResponse.AssignmentSummary toAssignmentSummary(Assignment assignment);
}