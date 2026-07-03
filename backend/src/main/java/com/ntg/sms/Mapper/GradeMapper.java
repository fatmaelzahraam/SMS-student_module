package com.ntg.sms.Mapper;

import com.ntg.sms.Entities.Dtos.Request.GradeRequest;
import com.ntg.sms.Entities.Dtos.Response.GradeResponse;
import com.ntg.sms.Entities.Grade;
import com.ntg.sms.Entities.Term;
import org.mapstruct.*;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface GradeMapper {

    @Mapping(source = "terms", target = "terms")
    GradeResponse toResponse(Grade grade);

    @Mapping(source = "id",   target = "id")
    GradeResponse.TermSummary toTermSummary(Term term);

    @Mapping(source = "request.id",   target = "id")
    @Mapping(source = "request.name", target = "name")
    @Mapping(source = "terms",        target = "terms")
    Grade toEntity(GradeRequest request, Set<Term> terms);

    @Mapping(source = "request.name", target = "name")
    @Mapping(source = "terms",        target = "terms")
    @Mapping(target = "id",           ignore = true)
    void updateEntity(@MappingTarget Grade grade, GradeRequest request, Set<Term> terms);
}