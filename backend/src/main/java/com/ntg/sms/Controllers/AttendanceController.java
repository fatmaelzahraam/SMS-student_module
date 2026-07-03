package com.ntg.sms.Controllers;

import com.ntg.sms.Entities.Dtos.Request.AttendanceRequest;
import com.ntg.sms.Entities.Dtos.Response.AttendaceResponse;
import com.ntg.sms.Service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    // Create Attendance
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AttendaceResponse createAttendance(@RequestBody AttendanceRequest request) {
        return attendanceService.createAttendance(request);
    }

    // Update Attendance
    @PutMapping("/{id}")
    public AttendaceResponse updateAttendance(
            @PathVariable Long id,
            @RequestBody AttendanceRequest request) {

        return attendanceService.updateAttendance(id, request);
    }

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

    // Delete Attendance
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAttendance(@PathVariable Long id) {
        attendanceService.deleteAttendance(id);
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
}