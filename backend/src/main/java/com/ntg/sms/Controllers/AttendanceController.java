package com.ntg.sms.Controllers;

import com.ntg.sms.Entities.Dtos.Response.AttendaceResponse;
import com.ntg.sms.Entities.Dtos.Response.AttendanceDailyResponse;
import com.ntg.sms.Entities.Dtos.Response.AttendanceMonthlyResponse;
import com.ntg.sms.Entities.Dtos.Response.AttendanceOverviewResponse;
import com.ntg.sms.Service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    // ── Admin endpoints (studentId in path) ─────────────────────────────────

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AttendaceResponse> getAttendanceById(@PathVariable Long id) {
        return ResponseEntity.ok(attendanceService.getAttendanceById(id));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AttendaceResponse>> getAllAttendance() {
        return ResponseEntity.ok(attendanceService.getAllAttendance());
    }

    @GetMapping("/student/{studentId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AttendaceResponse>> getAttendanceByStudent(
            @PathVariable Long studentId) {
        return ResponseEntity.ok(attendanceService.getAttendanceByStudent(studentId));
    }

    @GetMapping("/session/{sessionId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AttendaceResponse>> getAttendanceBySession(
            @PathVariable Long sessionId) {
        return ResponseEntity.ok(attendanceService.getAttendanceBySession(sessionId));
    }

    // ── Student-facing endpoints (resolved from JWT — no studentId param) ───

    /**
     * GET /api/v1/attendance/my
     * Full attendance history for the logged-in student.
     */
    @GetMapping("/my")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<List<AttendaceResponse>> getMyAttendance() {
        return ResponseEntity.ok(attendanceService.getMyAttendance());
    }

    /**
     * GET /api/v1/attendance/overview
     * Total / present / absent / late / percentage for the logged-in student.
     */
    @GetMapping("/overview")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<AttendanceOverviewResponse> getOverview() {
        return ResponseEntity.ok(attendanceService.getOverview());
    }

    /**
     * GET /api/v1/attendance/daily?date=2026-07-06
     * Day-level breakdown for the logged-in student.
     */
    @GetMapping("/daily")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<AttendanceDailyResponse> getDailyAttendance(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date) {

        return ResponseEntity.ok(attendanceService.getDailyAttendance(date));
    }

    /**
     * GET /api/v1/attendance/monthly?month=7&year=2026
     * Month-level breakdown for the logged-in student.
     */
    @GetMapping("/monthly")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<AttendanceMonthlyResponse> getMonthlyAttendance(
            @RequestParam int month,
            @RequestParam int year) {

        return ResponseEntity.ok(attendanceService.getMonthlyAttendance(month, year));
    }

    // ── Weekly chart helpers ─────────────────────────────────────────────────

    @GetMapping("/today/count")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<Double> getTodayAttendance() {
        return ResponseEntity.ok(attendanceService.getTodayAttendance());
    }

    @GetMapping("/weekly/count")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<List<Long>> getWeeklyAttendanceCounts(
            @RequestParam(defaultValue = "7") int weeks) {
        return ResponseEntity.ok(attendanceService.getWeeklyAttendanceCounts(weeks));
    }

    @GetMapping("/weekly/labels")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<List<String>> getWeeklyLabels(
            @RequestParam(defaultValue = "7") int weeks) {
        return ResponseEntity.ok(attendanceService.getWeeklyLabels(weeks));
    }
}