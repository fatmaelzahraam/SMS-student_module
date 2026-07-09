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


    // ─── HELPERS ─────────────────────────────────────────────────────────────

    private Class findClassOrThrow(Long id) {
        return classRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Class not found with id: " + id));
    }

}
