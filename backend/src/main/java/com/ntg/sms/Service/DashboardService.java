package com.ntg.sms.Service;

import com.ntg.sms.Entities.Dtos.Response.CourseResponse;
import com.ntg.sms.Entities.Dtos.Response.DashboardAttendanceOverView;
import com.ntg.sms.Entities.Dtos.Response.DashboardResponse;
import com.ntg.sms.Entities.Student;
import com.ntg.sms.Repositories.*;
import com.ntg.sms.Service.AttendanceService;
import com.ntg.sms.Service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final AttendanceRepository attendanceRepository;
    private final AssignmentRepository assignmentRepository;
    private final MarkRepository markRepository;
    private final StudentService studentService;
    private final AttendanceService attendanceService;
    private final DelayRepository delayRepository;
    private final StudentRepository studentRepository;

    @Transactional(readOnly = true)
    public DashboardResponse getDashboard() {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Student student = studentRepository.findByUser_Email(email).orElse(null);



        Double performance =
                markRepository.calculatePerformance(student.getId());

        Integer rank =
                markRepository.getStudentRank(student.getId());

        Long totalStudents =
                studentService.countStudents();

        return DashboardResponse.builder()
                .attendance(DashboardAttendanceOverView.builder()
                .presentPercentage(attendanceService.getPresentPercentage()).absenceCount(attendanceService.getAbsenceCount()).LateCount(delayRepository.countAllByStudent_Id(student.getId())).build())
                .rank(rank)
                .totalStudents(totalStudents)
                .build();
    }
}
