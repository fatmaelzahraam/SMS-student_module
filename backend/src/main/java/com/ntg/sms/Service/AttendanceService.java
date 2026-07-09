package com.ntg.sms.Service;

import com.ntg.sms.Entities.Dtos.Request.AttendanceRequest;
import com.ntg.sms.Entities.Dtos.Response.AttendaceResponse;
import com.ntg.sms.Entities.Attendance;
import com.ntg.sms.Entities.Dtos.Response.AttendanceDailyResponse;
import com.ntg.sms.Entities.Dtos.Response.AttendanceMonthlyResponse;
import com.ntg.sms.Entities.Dtos.Response.AttendanceOverviewResponse;
import com.ntg.sms.Entities.Session;
import com.ntg.sms.Entities.Student;
import com.ntg.sms.Mapper.AttendanceMapper;
import com.ntg.sms.Repositories.AttendanceRepository;
import com.ntg.sms.Repositories.SessionRepository;
import com.ntg.sms.Repositories.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final StudentRepository studentRepository;
    private final AttendanceMapper attendanceMapper;

    public AttendaceResponse getAttendanceById(Long id) {

        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Attendance not found"));

        return attendanceMapper.toResponse(attendance);
    }

    public List<AttendaceResponse> getAllAttendance() {

        return attendanceRepository.findAll()
                .stream()
                .map(attendanceMapper::toResponse)
                .toList();
    }

    public List<AttendaceResponse> getAttendanceByStudent(Long studentId) {

        return attendanceRepository.findByStudentId(studentId)
                .stream()
                .map(attendanceMapper::toResponse)
                .toList();
    }
    public List<AttendaceResponse> getMyAttendance(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Student student = studentRepository.findByUser_Email(email).orElse(null);
        return getAttendanceByStudent(student.getId());
    }

    public List<AttendaceResponse> getAttendanceBySession(Long sessionId) {

        return attendanceRepository.findBySessionId(sessionId)
                .stream()
                .map(attendanceMapper::toResponse)
                .toList();
    }

    public Double getTodayAttendance() {
     LocalDate today = LocalDate.now();
      LocalDateTime start = today.atStartOfDay();
     LocalDateTime end   = today.plusDays(1).atStartOfDay();
      return (double) attendanceRepository.countByWeek(start, end);
  }

    public List<Long> getWeeklyAttendanceCounts(int weeks) {
        List<Long> result = new ArrayList<>();
        // find the start of the current week (Sunday)
        LocalDate weekStart = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        // walk backwards from the oldest week to the current one
        LocalDate firstWeekStart = weekStart.minusWeeks(weeks - 1);
        for (int i = 0; i < weeks; i++) {
            LocalDate wStart = firstWeekStart.plusWeeks(i);
            LocalDate wEnd   = wStart.plusWeeks(1);
            long count = attendanceRepository.countByWeek(
                    wStart.atStartOfDay(),
                    wEnd.atStartOfDay()
            );
            result.add(count);
        }
        return result;
    }

    public List<String> getWeeklyLabels(int weeks) {
        List<String> labels = new ArrayList<>();
        LocalDate weekStart = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate firstWeekStart = weekStart.minusWeeks(weeks - 1);
        for (int i = 0; i < weeks; i++) {
            LocalDate wStart = firstWeekStart.plusWeeks(i);
            String monthAbbr = wStart.getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
            labels.add(monthAbbr + " " + wStart.getDayOfMonth());
        }
        return labels;
    }

    private Student getStudent(Long studentId) {
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));
    }

    public AttendanceOverviewResponse getOverview(Long studentId) {

        Student student = getStudent(studentId);

        long total   = attendanceRepository.countByStudent(student);
        long present = attendanceRepository.countByStudentAndStatus(student, 'P');
        long absent  = attendanceRepository.countByStudentAndStatus(student, 'A');
        long late    = attendanceRepository.countByStudentAndStatus(student, 'L');

        double percentage = total == 0 ? 0 : ((double) present / total) * 100;

        return AttendanceOverviewResponse.builder()
                .totalDays(total)
                .presentDays(present)
                .absentDays(absent)
                .lateDays(late)
                .attendancePercentage(percentage)
                .build();
    }

    public List<AttendaceResponse> getAttendanceHistory(Long studentId) {

        Student student = getStudent(studentId);

        return attendanceRepository.findByStudent(student)
                .stream()
                .map(attendanceMapper::toResponse)
                .toList();
    }

    public AttendanceDailyResponse getDailyAttendance(Long studentId, LocalDate date) {

        Student student = getStudent(studentId);

        List<Attendance> dailyAttendance = attendanceRepository.findByStudent(student)
                .stream()
                .filter(a -> a.getDateTime().toLocalDate().equals(date))
                .toList();

        long present = dailyAttendance.stream().filter(a -> a.getStatus() == 'P').count();
        long absent  = dailyAttendance.stream().filter(a -> a.getStatus() == 'A').count();
        long late    = dailyAttendance.stream().filter(a -> a.getStatus() == 'L').count();

        double percentage = dailyAttendance.isEmpty()
                ? 0
                : ((double) present / dailyAttendance.size()) * 100;

        return AttendanceDailyResponse.builder()
                .date(date)
                .totalSessions(dailyAttendance.size())
                .present(present)
                .absent(absent)
                .late(late)
                .attendancePercentage(percentage)
                .attendanceList(
                        dailyAttendance.stream()
                                .map(attendanceMapper::toResponse)
                                .toList()
                )
                .build();
    }

    public AttendanceMonthlyResponse getMonthlyAttendance(Long studentId, int month, int year) {

        Student student = getStudent(studentId);

        List<Attendance> monthlyAttendance = attendanceRepository.findByStudent(student)
                .stream()
                .filter(a -> a.getDateTime().getMonthValue() == month)
                .filter(a -> a.getDateTime().getYear() == year)
                .toList();

        long present = monthlyAttendance.stream().filter(a -> a.getStatus() == 'P').count();
        long absent  = monthlyAttendance.stream().filter(a -> a.getStatus() == 'A').count();
        long late    = monthlyAttendance.stream().filter(a -> a.getStatus() == 'L').count();

        double percentage = monthlyAttendance.isEmpty()
                ? 0
                : ((double) present / monthlyAttendance.size()) * 100;

        return AttendanceMonthlyResponse.builder()
                .month(month)
                .year(year)
                .present(present)
                .absent(absent)
                .late(late)
                .attendancePercentage(percentage)
                .build();
    }

    // ── Dashboard helpers (called by DashboardService) ───────────────────────

    public Double getPresentPercentage() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Student student = studentRepository.findByUser_Email(email)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));

        long total   = attendanceRepository.countByStudent(student);
        long present = attendanceRepository.countByStudentAndStatus(student, 'P');

        return total == 0 ? 0.0 : ((double) present / total) * 100;
    }

    public Long getAbsenceCount() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Student student = studentRepository.findByUser_Email(email)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));

        return attendanceRepository.countByStudentAndStatus(student, 'A');
    }


}