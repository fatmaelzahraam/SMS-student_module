package com.ntg.sms.Service;

import com.ntg.sms.Entities.Attendance;
import com.ntg.sms.Entities.Dtos.Response.AttendaceResponse;
import com.ntg.sms.Entities.Dtos.Response.AttendanceDailyResponse;
import com.ntg.sms.Entities.Dtos.Response.AttendanceMonthlyResponse;
import com.ntg.sms.Entities.Dtos.Response.AttendanceOverviewResponse;
import com.ntg.sms.Entities.Student;
import com.ntg.sms.Mapper.AttendanceMapper;
import com.ntg.sms.Repositories.AttendanceRepository;
import com.ntg.sms.Repositories.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final StudentRepository    studentRepository;
    private final AttendanceMapper     attendanceMapper;

    // ── Private helpers ──────────────────────────────────────────────────────

    /**
     * Resolves the currently authenticated student from the JWT.
     * Used by every student-facing endpoint so no studentId is
     * ever trusted from the request.
     */
    private Student getAuthenticatedStudent() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication().getName();
        return studentRepository.findByUser_Email(email)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));
    }

    private Student getStudentById(Long studentId) {
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + studentId));
    }

    // ── Basic CRUD (admin-facing) ────────────────────────────────────────────

    @Transactional(readOnly = true)
    public AttendaceResponse getAttendanceById(Long id) {
        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Attendance not found"));
        return attendanceMapper.toResponse(attendance);
    }

    @Transactional(readOnly = true)
    public List<AttendaceResponse> getAllAttendance() {
        return attendanceRepository.findAll()
                .stream()
                .map(attendanceMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<AttendaceResponse> getAttendanceByStudent(Long studentId) {
        return attendanceRepository.findByStudentId(studentId)
                .stream()
                .map(attendanceMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<AttendaceResponse> getAttendanceBySession(Long sessionId) {
        return attendanceRepository.findBySessionId(sessionId)
                .stream()
                .map(attendanceMapper::toResponse)
                .toList();
    }

    // ── Student-facing: resolved from JWT ───────────────────────────────────

    /**
     * Returns the full attendance history for the logged-in student.
     */
    @Transactional(readOnly = true)
    public List<AttendaceResponse> getMyAttendance() {
        Student student = getAuthenticatedStudent();
        return attendanceRepository.findByStudent(student)
                .stream()
                .map(attendanceMapper::toResponse)
                .toList();
    }

    /**
     * Overview for the logged-in student.
     *
     * 1 day = 8 sessions. Counts are at the DAY level:
     *   totalDays   = distinct school days with any attendance record
     *   presentDays = distinct days where the student had NO absent session
     *                 (a day with only P or L records counts as present)
     *   absentDays  = distinct days where the student had at least 1 absent session
     *   percentage  = presentDays / totalDays * 100  (rounded to 2 decimal places)
     *
     * Day-level grouping is done in Java to avoid Oracle-unsafe JPQL date functions.
     */
    @Transactional(readOnly = true)
    public AttendanceOverviewResponse getOverview() {
        Student student = getAuthenticatedStudent();

        List<Attendance> all = attendanceRepository.findAllByStudentOrderByDateTime(student);

        // Group records by their calendar date
        java.util.Map<LocalDate, List<Attendance>> byDay = all.stream()
                .collect(java.util.stream.Collectors.groupingBy(
                        a -> a.getDateTime().toLocalDate()
                ));

        long totalDays  = byDay.size();

        // A day is "absent"  → at least 1 session has status 'A'
        long absentDays  = byDay.values().stream()
                .filter(sessions -> sessions.stream().anyMatch(a -> a.getStatus() == 'A'))
                .count();

        // A day is "late"    → at least 1 session has status 'L', but NO session has 'A'
        long lateDays    = byDay.values().stream()
                .filter(sessions -> sessions.stream().noneMatch(a -> a.getStatus() == 'A')
                        && sessions.stream().anyMatch(a -> a.getStatus() == 'L'))
                .count();

        // A day is "present" → all sessions are 'P' (no A, no L)
        long presentDays = totalDays - absentDays - lateDays;

        double percentage = totalDays == 0 ? 0
                : Math.round(((double) presentDays / totalDays) * 100 * 100.0) / 100.0;

        return AttendanceOverviewResponse.builder()
                .totalDays(totalDays)
                .presentDays(presentDays)
                .absentDays(absentDays)
                .lateDays(lateDays)
                .attendancePercentage(percentage)
                .build();
    }

    /**
     * Daily breakdown for the logged-in student on a specific date.
     */
    @Transactional(readOnly = true)
    public AttendanceDailyResponse getDailyAttendance(LocalDate date) {
        Student student = getAuthenticatedStudent();

        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay   = date.plusDays(1).atStartOfDay();

        // Already ordered by dateTime from the repo query
        List<Attendance> daily = attendanceRepository.findDailyAttendance(student, startOfDay, endOfDay);

        long present = daily.stream().filter(a -> a.getStatus() == 'P').count();
        long absent  = daily.stream().filter(a -> a.getStatus() == 'A').count();
        long late    = daily.stream().filter(a -> a.getStatus() == 'L').count();

        double percentage = daily.isEmpty()
                ? 0
                : Math.round(((double) present / daily.size()) * 100 * 100.0) / 100.0;

        // Assign session number 1-8 by position in the sorted list
        List<AttendaceResponse> attendanceList = new ArrayList<>();
        for (int i = 0; i < daily.size(); i++) {
            AttendaceResponse response = attendanceMapper.toResponse(daily.get(i));
            response.setSessionNumber(i + 1);
            attendanceList.add(response);
        }

        return AttendanceDailyResponse.builder()
                .date(date)
                .totalSessions(daily.size())
                .present(present)
                .absent(absent)
                .late(late)
                .attendancePercentage(percentage)
                .attendanceList(attendanceList)
                .build();
    }

    /**
     * Monthly breakdown for the logged-in student.
     */
    @Transactional(readOnly = true)
    public AttendanceMonthlyResponse getMonthlyAttendance(int month, int year) {
        Student student = getAuthenticatedStudent();

        // Use date-range bounds — Oracle does not support MONTH()/YEAR() in JPQL
        LocalDateTime startOfMonth     = LocalDate.of(year, month, 1).atStartOfDay();
        LocalDateTime startOfNextMonth = startOfMonth.plusMonths(1);

        List<Attendance> monthly =
                attendanceRepository.findByStudentAndMonthAndYear(student, startOfMonth, startOfNextMonth);

        long present = monthly.stream().filter(a -> a.getStatus() == 'P').count();
        long absent  = monthly.stream().filter(a -> a.getStatus() == 'A').count();
        long late    = monthly.stream().filter(a -> a.getStatus() == 'L').count();

        double percentage = monthly.isEmpty()
                ? 0
                : ((double) present / monthly.size()) * 100;

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

    /**
     * Attendance percentage for the logged-in student.
     * Used by DashboardService — do NOT expose as a controller endpoint.
     */
    public Double getPresentPercentage() {
        Student student = getAuthenticatedStudent();
        long total   = attendanceRepository.countByStudent(student);
        long present = attendanceRepository.countByStudentAndStatus(student, 'P');
        return total == 0 ? 0.0 : ((double) present / total) * 100;
    }

    /**
     * Absence count for the logged-in student.
     * Used by DashboardService — do NOT expose as a controller endpoint.
     */
    public Long getAbsenceCount() {
        Student student = getAuthenticatedStudent();
        return attendanceRepository.countByStudentAndStatus(student, 'A');
    }

    // ── Weekly chart helpers ─────────────────────────────────────────────────

    @Transactional(readOnly = true)
    public Double getTodayAttendance() {
        Student student = getAuthenticatedStudent();
        LocalDate today = LocalDate.now();
        LocalDateTime start = today.atStartOfDay();
        LocalDateTime end   = today.plusDays(1).atStartOfDay();
        // Count only for the authenticated student
        return (double) attendanceRepository.countByStudentAndDateTimeBetween(student, start, end);
    }

    @Transactional(readOnly = true)
    public List<Long> getWeeklyAttendanceCounts(int weeks) {
        Student student = getAuthenticatedStudent();
        List<Long> result = new ArrayList<>();
        LocalDate weekStart     = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        LocalDate firstWeekStart = weekStart.minusWeeks(weeks - 1);
        for (int i = 0; i < weeks; i++) {
            LocalDate wStart = firstWeekStart.plusWeeks(i);
            LocalDate wEnd   = wStart.plusWeeks(1);
            long count = attendanceRepository.countByStudentAndDateTimeBetween(
                    student,
                    wStart.atStartOfDay(),
                    wEnd.atStartOfDay()
            );
            result.add(count);
        }
        return result;
    }

    @Transactional(readOnly = true)
    public List<String> getWeeklyLabels(int weeks) {
        List<String> labels = new ArrayList<>();
        LocalDate weekStart      = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate firstWeekStart = weekStart.minusWeeks(weeks - 1);
        for (int i = 0; i < weeks; i++) {
            LocalDate wStart = firstWeekStart.plusWeeks(i);
            String monthAbbr = wStart.getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
            labels.add(monthAbbr + " " + wStart.getDayOfMonth());
        }
        return labels;
    }
}