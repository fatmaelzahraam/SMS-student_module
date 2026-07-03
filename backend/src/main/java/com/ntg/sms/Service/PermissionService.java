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

    // ─── CREATE ───────────────────────────────────────────────────────────────

    @Transactional
    public PermissionResponse createPermission(PermissionRequest request) {
        Student student = findStudentOrThrow(request.getStudentId());

        // Default date to today if not provided (mirrors @ColumnDefault("sysdate"))
        if (request.getDate() == null) {
            request.setDate(LocalDate.now());
        }

        Permission saved = permissionRepository.save(permissionMapper.toEntity(request, student));
        return permissionMapper.toResponse(saved);
    }

    // ─── UPDATE ───────────────────────────────────────────────────────────────

    @Transactional
    public PermissionResponse updatePermission(Long id, PermissionRequest request) {
        Permission permission = findPermissionOrThrow(id);
        Student student = findStudentOrThrow(request.getStudentId());

        if (request.getDate() == null) {
            request.setDate(permission.getDate());
        }

        permissionMapper.updateEntity(permission, request, student);
        return permissionMapper.toResponse(permissionRepository.save(permission));
    }

    // ─── DELETE ───────────────────────────────────────────────────────────────

    @Transactional
    public void deletePermission(Long id) {
        if (!permissionRepository.existsById(id)) {
            throw new EntityNotFoundException("Permission not found with id: " + id);
        }
        permissionRepository.deleteById(id);
    }

    // ─── HELPERS ─────────────────────────────────────────────────────────────

    private Permission findPermissionOrThrow(Long id) {
        return permissionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Permission not found with id: " + id));
    }

    private Student findStudentOrThrow(Long studentId) {
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + studentId));
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
