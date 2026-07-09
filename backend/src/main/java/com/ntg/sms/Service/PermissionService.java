package com.ntg.sms.Service;


import com.ntg.sms.Entities.Dtos.Request.PermissionRequest;
import com.ntg.sms.Entities.Dtos.Response.PermissionResponse;
import com.ntg.sms.Entities.Permission;
import com.ntg.sms.Entities.Student;
import com.ntg.sms.Mapper.PermissionMapper;
import com.ntg.sms.Repositories.PermissionRepository;
import com.ntg.sms.Repositories.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PermissionService {

    private final PermissionRepository permissionRepository;
    private final StudentRepository studentRepository;
    private final PermissionMapper permissionMapper;

    // ─── READ ─────────────────────────────────────────────────────────────────

    @Transactional(readOnly = true)
    public List<PermissionResponse> getAllPermissions() {
        return permissionRepository.findAll()
                .stream()
                .map(permissionMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PermissionResponse getPermissionById(Long id) {
        return permissionMapper.toResponse(findPermissionOrThrow(id));
    }

    @Transactional(readOnly = true)
    public List<PermissionResponse> getPermissionsByStudent(Long studentId) {
        assertStudentExists(studentId);
        return permissionRepository.findByStudentId(studentId)
                .stream()
                .map(permissionMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PermissionResponse> getPermissionsByDateRange(LocalDate from, LocalDate to) {
        validateDateRange(from, to);
        return permissionRepository.findByDateBetween(from, to)
                .stream()
                .map(permissionMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PermissionResponse> getPermissionsByStudentAndDateRange(
            Long studentId, LocalDate from, LocalDate to) {
        assertStudentExists(studentId);
        validateDateRange(from, to);
        return permissionRepository.findByStudentIdAndDateBetween(studentId, from, to)
                .stream()
                .map(permissionMapper::toResponse)
                .collect(Collectors.toList());
    }


    // ─── HELPERS ─────────────────────────────────────────────────────────────

    private Permission findPermissionOrThrow(Long id) {
        return permissionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Permission not found with id: " + id));
    }

    private void assertStudentExists(Long studentId) {
        if (!studentRepository.existsById(studentId))
            throw new EntityNotFoundException("Student not found with id: " + studentId);
    }

    private void validateDateRange(LocalDate from, LocalDate to) {
        if (from.isAfter(to)) {
            throw new IllegalArgumentException("'from' date must not be after 'to' date.");
        }
    }
}
