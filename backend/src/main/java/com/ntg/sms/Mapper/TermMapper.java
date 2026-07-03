package com.ntg.sms.Mapper;

import com.ntg.sms.Entities.Dtos.Response.TermResponse;
import com.ntg.sms.Entities.Term;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface TermMapper {
    Set<TermResponse> toResponse(Set<Term> term);
}
