package com.ntg.sms.Service;

import com.ntg.sms.Entities.Dtos.Request.ClassRequest;
import com.ntg.sms.Entities.Dtos.Response.ClassResponse;
import com.ntg.sms.Entities.Class;
import com.ntg.sms.Entities.Grade;
import com.ntg.sms.Mapper.ClassMapper;
import com.ntg.sms.Repositories.ClassRepository;
import com.ntg.sms.Repositories.GradeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClassService {

    private final ClassRepository classRepository;
    private final GradeRepository gradeRepository;
    private final ClassMapper classMapper;

    // ─── READ ────────────────────────────────────────────────────────────────

    @Transactional(readOnly = true)
    public List<ClassResponse> getAllClasses() {
        return classRepository.findAll()
                .stream()
                .map(classMapper::toResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ClassResponse getClassById(Long id) {
        Class classEntity = findClassOrThrow(id);
        return classMapper.toResponse(classEntity);
    }

    @Transactional(readOnly = true)
    public List<ClassResponse> getClassesByGrade(Long gradeId) {
        if (!gradeRepository.existsById(gradeId)) {
            throw new EntityNotFoundException("Grade not found with id: " + gradeId);
        }
        return classRepository.findByGradeId(gradeId)
                .stream()
                .map(classMapper::toResponse)
                .collect(Collectors.toList());
    }

    // ─── CREATE ───────────────────────────────────────────────────────────────

    @Transactional
    public ClassResponse createClass(ClassRequest request) {
        Grade grade = findGradeOrThrow(request.getGradeId());

        if (classRepository.existsByNameAndGradeId(request.getName(), request.getGradeId())) {
            throw new IllegalArgumentException(
                    "A class named '" + request.getName() + "' already exists in this grade.");
        }

        Class classEntity = classMapper.toEntity(request, grade);
        Class saved = classRepository.save(classEntity);
        return classMapper.toResponse(saved);
    }

    // ─── UPDATE ───────────────────────────────────────────────────────────────

    @Transactional
    public ClassResponse updateClass(Long id, ClassRequest request) {
        Class classEntity = findClassOrThrow(id);
        Grade grade = findGradeOrThrow(request.getGradeId());

        // Check name conflict only if name or grade changed
        boolean nameChanged = !classEntity.getName().equals(request.getName());
        boolean gradeChanged = !classEntity.getGrade().getId().equals(request.getGradeId());

        if ((nameChanged || gradeChanged)
                && classRepository.existsByNameAndGradeId(request.getName(), request.getGradeId())) {
            throw new IllegalArgumentException(
                    "A class named '" + request.getName() + "' already exists in this grade.");
        }

        classMapper.updateEntity(classEntity, request, grade);
        Class updated = classRepository.save(classEntity);
        return classMapper.toResponse(updated);
    }

    // ─── DELETE ───────────────────────────────────────────────────────────────

    @Transactional
    public void deleteClass(Long id) {
        if (!classRepository.existsById(id)) {
            throw new EntityNotFoundException("Class not found with id: " + id);
        }
        classRepository.deleteById(id);
    }

    // ─── HELPERS ─────────────────────────────────────────────────────────────

    private Class findClassOrThrow(Long id) {
        return classRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Class not found with id: " + id));
    }

    private Grade findGradeOrThrow(Long gradeId) {
        return gradeRepository.findById(gradeId)
                .orElseThrow(() -> new EntityNotFoundException("Grade not found with id: " + gradeId));
    }
}
