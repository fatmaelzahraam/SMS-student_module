package com.ntg.sms.Mapper;

import com.ntg.sms.Entities.Course;
import com.ntg.sms.Entities.Dtos.Request.MarkRequest;
import com.ntg.sms.Entities.Dtos.Response.MarkResponse;
import com.ntg.sms.Entities.Mark;
import com.ntg.sms.Entities.MarksType;
import com.ntg.sms.Entities.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface MarkMapper {

    @Mapping(source = "course.id",         target = "courseId")
    @Mapping(source = "course.courseName", target = "courseName")
    @Mapping(source = "user.id",           target = "userId")
    @Mapping(target = "userName",          expression = "java(mark.getUser().getFirstName() + \" \" + mark.getUser().getLastName())")
    @Mapping(source = "type.id",           target = "typeId")
    @Mapping(source = "type.type",         target = "typeName")
    MarkResponse toResponse(Mark mark);

    @Mapping(source = "request.id",           target = "id")
    @Mapping(source = "request.feedbackDate", target = "feedbackDate")
    @Mapping(source = "request.feedback",     target = "feedback")
    @Mapping(source = "request.notes",        target = "notes")
    @Mapping(source = "request.isApproved",   target = "isApproved")
    @Mapping(source = "request.score",        target = "score")
    @Mapping(source = "request.maxScore",     target = "maxScore")
    @Mapping(source = "course",               target = "course")
    @Mapping(source = "user",                 target = "user")
    @Mapping(source = "type",                 target = "type")
    Mark toEntity(MarkRequest request, Course course, User user, MarksType type);

    @Mapping(source = "request.feedbackDate", target = "feedbackDate")
    @Mapping(source = "request.feedback",     target = "feedback")
    @Mapping(source = "request.notes",        target = "notes")
    @Mapping(source = "request.isApproved",   target = "isApproved")
    @Mapping(source = "request.score",        target = "score")
    @Mapping(source = "request.maxScore",     target = "maxScore")
    @Mapping(source = "course",               target = "course")
    @Mapping(source = "user",                 target = "user")
    @Mapping(source = "type",                 target = "type")
    @Mapping(target = "id",                   ignore = true)
    void updateEntity(@MappingTarget Mark mark, MarkRequest request,
                      Course course, User user, MarksType type);
}