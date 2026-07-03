//package com.ntg.sms.Controllers;
//
//import com.ntg.sms.Entities.Dtos.Response.DashboardResponse;
//import com.ntg.sms.Service.*;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api/v1/dashboard/")
//public class DashboardController {
//
//    private static final int WEEKLY_WEEKS  = 8;
//    private static final int MONTHLY_MONTHS = 6;
//
//    private final StudentService studentService;
//    private final ClassService classService;
//    private final AttendanceService attendanceService;
//    private final AssignmentService assignmentService;
//
//    @GetMapping
//    public DashboardResponse getDashboard() {
//
//        // ── Attendance weekly chart ─────────────────────────────────────────
//        List<String> attendanceLabels = attendanceService.getWeeklyLabels(WEEKLY_WEEKS);
//        List<Double> attendanceData   = attendanceService
//                .getWeeklyAttendanceCounts(WEEKLY_WEEKS)
//                .stream()
//                .map(Long::doubleValue)
//                .collect(Collectors.toList());
//
//      }
//}
