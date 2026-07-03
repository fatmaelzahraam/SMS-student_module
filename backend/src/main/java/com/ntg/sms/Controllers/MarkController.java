package com.ntg.sms.Controllers;

import com.ntg.sms.Entities.Dtos.Request.MarkRequest;
import com.ntg.sms.Entities.Dtos.Response.MarkResponse;
import com.ntg.sms.Service.MarkService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/marks")
@RequiredArgsConstructor
public class MarkController {

    private final MarkService markService;

    // ─── GET ALL ─────────────────────────────────────────────────────────────

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<MarkResponse>> getAllMarks() {
        return ResponseEntity.ok(markService.getAllMarks());
    }

    // ─── GET BY ID ────────────────────────────────────────────────────────────

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    public ResponseEntity<MarkResponse> getMarkById(@PathVariable Long id) {
        return ResponseEntity.ok(markService.getMarkById(id));
    }

    // ─── GET BY USER ──────────────────────────────────────────────────────────

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER', 'STUDENT')")
    public ResponseEntity<List<MarkResponse>> getMarksByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(markService.getMarksByUser(userId));
    }

    // ─── GET BY COURSE ────────────────────────────────────────────────────────

    @GetMapping("/course/{courseId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    public ResponseEntity<List<MarkResponse>> getMarksByCourse(@PathVariable Long courseId) {
        return ResponseEntity.ok(markService.getMarksByCourse(courseId));
    }

    // ─── GET BY USER + COURSE ─────────────────────────────────────────────────

    @GetMapping("/user/{userId}/course/{courseId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER', 'STUDENT')")
    public ResponseEntity<List<MarkResponse>> getMarksByUserAndCourse(
            @PathVariable Long userId,
            @PathVariable Long courseId) {
        return ResponseEntity.ok(markService.getMarksByUserAndCourse(userId, courseId));
    }

    // ─── GET APPROVED MARKS BY USER ───────────────────────────────────────────

    @GetMapping("/user/{userId}/approved")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER', 'STUDENT')")
    public ResponseEntity<List<MarkResponse>> getApprovedMarksByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(markService.getApprovedMarksByUser(userId));
    }

    // ─── GET TOTAL APPROVED SCORE ─────────────────────────────────────────────

    @GetMapping("/user/{userId}/course/{courseId}/total-score")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER', 'STUDENT')")
    public ResponseEntity<Long> getTotalApprovedScore(
            @PathVariable Long userId,
            @PathVariable Long courseId) {
        return ResponseEntity.ok(markService.getTotalApprovedScore(userId, courseId));
    }

    // ─── CREATE ───────────────────────────────────────────────────────────────

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    public ResponseEntity<MarkResponse> createMark(@Valid @RequestBody MarkRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(markService.createMark(request));
    }

    // ─── UPDATE ───────────────────────────────────────────────────────────────

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    public ResponseEntity<MarkResponse> updateMark(
            @PathVariable Long id,
            @Valid @RequestBody MarkRequest request) {
        return ResponseEntity.ok(markService.updateMark(id, request));
    }

    // ─── APPROVE / REVOKE ─────────────────────────────────────────────────────

    @PatchMapping("/{id}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MarkResponse> approveMark(@PathVariable Long id) {
        return ResponseEntity.ok(markService.approveMark(id));
    }

    @PatchMapping("/{id}/revoke")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MarkResponse> revokeMark(@PathVariable Long id) {
        return ResponseEntity.ok(markService.revokeMark(id));
    }

    // ─── DELETE ───────────────────────────────────────────────────────────────

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteMark(@PathVariable Long id) {
        markService.deleteMark(id);
        return ResponseEntity.noContent().build();
    }
}