package com.ntg.sms.Controllers;

import com.ntg.sms.Entities.Class;
import com.ntg.sms.Entities.Dtos.Response.SessionResponse;
import com.ntg.sms.Service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/schedule")
@RequiredArgsConstructor
public class SessionController {

    private final SessionService sessions;

    // ── Class schedule for a student ─────────────────────────────────────────
    @GetMapping("/student/{studentId}")
    @PreAuthorize("hasRole('STUDENT') or hasRole('ADMIN')")
    public ResponseEntity<List<SessionResponse>> getClassSchedule(
            @PathVariable Long studentId) {
        return ResponseEntity.ok(sessions.getClassSessionsByStudent(studentId));
    }

    // ── Month exam schedule for a student ────────────────────────────────────
    @GetMapping("/student/{studentId}/exams/month")
    @PreAuthorize("hasRole('STUDENT') or hasRole('ADMIN')")
    public ResponseEntity<List<SessionResponse>> getMonthExams(
            @PathVariable Long studentId) {
        return ResponseEntity.ok(sessions.getMonthExamsByStudent(studentId));
    }

    // ── Final exam schedule for a student ────────────────────────────────────
    @GetMapping("/student/{studentId}/exams/final")
    @PreAuthorize("hasRole('STUDENT') or hasRole('ADMIN')")
    public ResponseEntity<List<SessionResponse>> getFinalExams(
            @PathVariable Long studentId) {
        return ResponseEntity.ok(sessions.getFinalExamsByStudent(studentId));
    }

    // ── Legacy: all sessions by classId ──────────────────────────────────────
    @GetMapping("/class/{classId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<SessionResponse>> getAllByClass(
            @PathVariable Long classId) {
        return ResponseEntity.ok(sessions.allSessions(classId));
    }

    @GetMapping("/classes")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Class>> getAllClasses() {
        return ResponseEntity.ok(sessions.getAllClasses());
    }
}