package com.ntg.sms.Controllers;

import com.ntg.sms.Entities.Dtos.Response.DashboardResponse;
import com.ntg.sms.Service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/dashboard/")
public class DashboardController {

    private static final int WEEKLY_WEEKS  = 8;
    private static final int MONTHLY_MONTHS = 6;

    private final StudentService studentService;
    private final ClassService classService;
    private final AttendanceService attendanceService;
    private final AssignmentService assignmentService;
    private final DashboardService dashboardService;

    @GetMapping
    public DashboardResponse getDashboard() {
        return dashboardService.getDashboard();
    }
}