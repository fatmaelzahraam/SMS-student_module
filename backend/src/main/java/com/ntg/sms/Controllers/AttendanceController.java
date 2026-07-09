package com.ntg.sms.Controllers;

import com.ntg.sms.Entities.Dtos.Request.AttendanceRequest;
import com.ntg.sms.Entities.Dtos.Response.AttendaceResponse;
import com.ntg.sms.Entities.Dtos.Response.AttendanceDailyResponse;
import com.ntg.sms.Entities.Dtos.Response.AttendanceMonthlyResponse;
import com.ntg.sms.Entities.Dtos.Response.AttendanceOverviewResponse;
import com.ntg.sms.Service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    // Get Attendance by ID
    @GetMapping("/{id}")
    public AttendaceResponse getAttendanceById(@PathVariable Long id) {
        return attendanceService.getAttendanceById(id);
    }

    // Get All Attendance
    @GetMapping
    public List<AttendaceResponse> getAllAttendance() {
        return attendanceService.getAllAttendance();
    }

    // Get Attendance by Student ID
    @GetMapping("/student/{studentId}")
    public List<AttendaceResponse> getAttendanceByStudent(
            @PathVariable Long studentId) {

        return attendanceService.getAttendanceByStudent(studentId);
    }

    // Get Attendance by Session ID
    @GetMapping("/session/{sessionId}")
    public List<AttendaceResponse> getAttendanceBySession(
            @PathVariable Long sessionId) {

        return attendanceService.getAttendanceBySession(sessionId);
    }


    // Get Today's Attendance Count
    @GetMapping("/today/count")
    public Double getTodayAttendance() {
        return attendanceService.getTodayAttendance();
    }

    // Get Weekly Attendance Counts
    @GetMapping("/weekly/count")
    public List<Long> getWeeklyAttendanceCounts(
            @RequestParam(defaultValue = "7") int weeks) {

        return attendanceService.getWeeklyAttendanceCounts(weeks);
    }

    // Get Weekly Labels
    @GetMapping("/weekly/labels")
    public List<String> getWeeklyLabels(
            @RequestParam(defaultValue = "7") int weeks) {

        return attendanceService.getWeeklyLabels(weeks);
    }


    @GetMapping("/overview")
    public ResponseEntity<AttendanceOverviewResponse> getOverview(
            @RequestParam Long studentId) {

        return ResponseEntity.ok(
                attendanceService.getOverview(studentId));
    }

    @GetMapping("/daily")
    public ResponseEntity<AttendanceDailyResponse> getDailyAttendance(

            @RequestParam Long studentId,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date) {

        return ResponseEntity.ok(
                attendanceService.getDailyAttendance(studentId, date));
    }

    @GetMapping("/monthly")
    public ResponseEntity<AttendanceMonthlyResponse> getMonthlyAttendance(

            @RequestParam Long studentId,

            @RequestParam int month,

            @RequestParam int year) {

        return ResponseEntity.ok(
                attendanceService.getMonthlyAttendance(studentId, month, year));
    }
}