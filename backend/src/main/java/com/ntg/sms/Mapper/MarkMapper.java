package com.ntg.sms.Mapper;

import com.ntg.sms.Entities.Dtos.Response.MarkResponse;
import com.ntg.sms.Entities.Mark;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MarkMapper {

    @Mapping(source = "course.id",         target = "courseId")
    @Mapping(source = "course.courseName", target = "courseName")
    @Mapping(source = "student.id",        target = "studentId")
    @Mapping(source = "type.id",           target = "typeId")
    @Mapping(source = "type.typeName",     target = "typeName")
    @Mapping(source = "feedbackDate",      target = "feedbackDate")
    @Mapping(source = "feedback",          target = "feedback")
    @Mapping(source = "notes",             target = "notes")
    @Mapping(source = "score",             target = "score")
    @Mapping(source = "maxScore",          target = "maxScore")
    @Mapping(source = "isApproved",        target = "isApproved")
    @Mapping(target = "studentName",
            expression = "java(mark.getStudent().getUser() != null " +
                    "? mark.getStudent().getUser().getFirstName() + \" \" + mark.getStudent().getUser().getLastName() " +
                    ": \"\")")
    MarkResponse toResponse(Mark mark);
}