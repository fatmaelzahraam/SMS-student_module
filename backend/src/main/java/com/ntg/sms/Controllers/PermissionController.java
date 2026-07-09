package com.ntg.sms.Controllers;


import com.ntg.sms.Entities.Dtos.Request.PermissionRequest;
import com.ntg.sms.Entities.Dtos.Response.PermissionResponse;
import com.ntg.sms.Service.PermissionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/permissions")
@RequiredArgsConstructor
public class PermissionController {

    private final PermissionService permissionService;

    // ─── GET BY ID ────────────────────────────────────────────────────────────

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STUDENT')")
    public ResponseEntity<PermissionResponse> getPermissionById(@PathVariable Long id) {
        return ResponseEntity.ok(permissionService.getPermissionById(id));
    }

    // ─── GET BY STUDENT ───────────────────────────────────────────────────────

    @GetMapping("/student/{studentId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STUDENT')")
    public ResponseEntity<List<PermissionResponse>> getPermissionsByStudent(
            @PathVariable Long studentId) {
        return ResponseEntity.ok(permissionService.getPermissionsByStudent(studentId));
    }

    // ─── GET BY STUDENT + DATE RANGE ─────────────────────────────────────────

    @GetMapping("/student/{studentId}/date/range")
    @PreAuthorize("hasAnyRole('ADMIN', 'STUDENT')")
    public ResponseEntity<List<PermissionResponse>> getPermissionsByStudentAndDateRange(
            @PathVariable Long studentId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return ResponseEntity.ok(
                permissionService.getPermissionsByStudentAndDateRange(studentId, from, to));
    }

}
