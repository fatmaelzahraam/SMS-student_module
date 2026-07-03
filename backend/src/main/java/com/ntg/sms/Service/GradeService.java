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

    // ─── CREATE ───────────────────────────────────────────────────────────────

    @Transactional
    public GradeResponse createGrade(GradeRequest request) {
        if (gradeRepository.existsById(request.getId())) {
            throw new IllegalArgumentException("Grade with id " + request.getId() + " already exists.");
        }
        if (gradeRepository.existsByName(request.getName())) {
            throw new IllegalArgumentException("Grade with name '" + request.getName() + "' already exists.");
        }

        Set<Term> terms = resolveTerms(request.getTermIds());
        Grade grade = gradeMapper.toEntity(request, terms);
        return gradeMapper.toResponse(gradeRepository.save(grade));
    }

    // ─── UPDATE ───────────────────────────────────────────────────────────────

    @Transactional
    public GradeResponse updateGrade(Long id, GradeRequest request) {
        Grade grade = findGradeOrThrow(id);

        if (!grade.getName().equals(request.getName()) && gradeRepository.existsByName(request.getName())) {
            throw new IllegalArgumentException("Grade with name '" + request.getName() + "' already exists.");
        }

        Set<Term> terms = resolveTerms(request.getTermIds());
        gradeMapper.updateEntity(grade, request, terms);
        return gradeMapper.toResponse(gradeRepository.save(grade));
    }

    // ─── TERM MANAGEMENT ──────────────────────────────────────────────────────

    @Transactional
    public GradeResponse addTerm(Long gradeId, Long termId) {
        Grade grade = findGradeOrThrow(gradeId);
        Term term = findTermOrThrow(termId);
        grade.getTerms().add(term);
        return gradeMapper.toResponse(gradeRepository.save(grade));
    }

    @Transactional
    public GradeResponse removeTerm(Long gradeId, Long termId) {
        Grade grade = findGradeOrThrow(gradeId);
        grade.getTerms().removeIf(t -> t.getId().equals(termId));
        return gradeMapper.toResponse(gradeRepository.save(grade));
    }

    // ─── DELETE ───────────────────────────────────────────────────────────────

    @Transactional
    public void deleteGrade(Long id) {
        if (!gradeRepository.existsById(id)) {
            throw new EntityNotFoundException("Grade not found with id: " + id);
        }
        gradeRepository.deleteById(id);
    }

    // ─── HELPERS ─────────────────────────────────────────────────────────────

    private Grade findGradeOrThrow(Long id) {
        return gradeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Grade not found with id: " + id));
    }

    private Term findTermOrThrow(Long termId) {
        return termRepository.findById(termId)
                .orElseThrow(() -> new EntityNotFoundException("Term not found with id: " + termId));
    }

    private Set<Term> resolveTerms(Set<Long> ids) {
        if (ids == null || ids.isEmpty()) return Collections.emptySet();
        Set<Term> found = termRepository.findAllById(ids)
                .stream().collect(Collectors.toSet());
        if (found.size() != ids.size()) {
            throw new EntityNotFoundException("One or more term IDs are invalid.");
        }
        return found;
    }
}
