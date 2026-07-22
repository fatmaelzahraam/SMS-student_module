package com.ntg.sms.Controllers;

import com.ntg.sms.Entities.Dtos.Response.PermissionResponse;
import com.ntg.sms.Service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
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

    // ─── Student-facing endpoints MUST come before /{id} ─────────────────────
    // Spring matches routes top-to-bottom. If /{id} is declared first,
    // it intercepts "/my" and tries to parse "my" as a Long → 400 Bad Request.

    /**
     * GET /api/v1/permissions/my
     */
    @GetMapping("/my")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<List<PermissionResponse>> getMyPermissions() {
        return ResponseEntity.ok(permissionService.getMyPermissions());
    }

    /**
     * GET /api/v1/permissions/my/date/range?from=2026-07-01&to=2026-07-31
     */
    @GetMapping("/my/date/range")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<List<PermissionResponse>> getMyPermissionsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return ResponseEntity.ok(
                permissionService.getMyPermissionsByDateRange(from, to));
    }

    // ─── Admin endpoints ──────────────────────────────────────────────────────

    /**
     * GET /api/v1/permissions/{id}
     * Declared AFTER /my so Spring doesn't confuse "my" for a Long id.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PermissionResponse> getPermissionById(@PathVariable Long id) {
        return ResponseEntity.ok(permissionService.getPermissionById(id));
    }

    @GetMapping("/student/{studentId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<PermissionResponse>> getPermissionsByStudent(
            @PathVariable Long studentId) {
        return ResponseEntity.ok(permissionService.getPermissionsByStudent(studentId));
    }

    @GetMapping("/student/{studentId}/date/range")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<PermissionResponse>> getPermissionsByStudentAndDateRange(
            @PathVariable Long studentId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return ResponseEntity.ok(
                permissionService.getPermissionsByStudentAndDateRange(studentId, from, to));
    }
}