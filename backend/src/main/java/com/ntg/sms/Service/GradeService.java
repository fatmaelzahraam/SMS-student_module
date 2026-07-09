package com.ntg.sms.Service;


import com.ntg.sms.Entities.Dtos.Request.GradeRequest;
import com.ntg.sms.Entities.Dtos.Response.GradeResponse;
import com.ntg.sms.Entities.Grade;
import com.ntg.sms.Entities.Term;
import com.ntg.sms.Mapper.GradeMapper;
import com.ntg.sms.Repositories.GradeRepository;
import com.ntg.sms.Repositories.TermRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GradeService {

    private final GradeRepository gradeRepository;
    private final TermRepository termRepository;
    private final GradeMapper gradeMapper;

    // ─── READ ─────────────────────────────────────────────────────────────────

    @Transactional(readOnly = true)
    public List<GradeResponse> getAllGrades() {
        return gradeRepository.findAll()
                .stream()
                .map(gradeMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public GradeResponse getGradeById(Long id) {
        Grade grade = gradeRepository.findByIdWithTerms(id)
                .orElseThrow(() -> new EntityNotFoundException("Grade not found with id: " + id));
        return gradeMapper.toResponse(grade);
    }

    @Transactional(readOnly = true)
    public List<GradeResponse> getGradesByTerm(Long termId) {
        if (!termRepository.existsById(termId)) {
            throw new EntityNotFoundException("Term not found with id: " + termId);
        }
        return gradeRepository.findByTermsId(termId)
                .stream()
                .map(gradeMapper::toResponse)
                .collect(Collectors.toList());
    }

}
