package com.ntg.sms.Service;

import com.ntg.sms.Entities.Dtos.Request.AttendanceRequest;
import com.ntg.sms.Entities.Dtos.Response.AttendaceResponse;
import com.ntg.sms.Entities.Attendance;
import com.ntg.sms.Entities.Session;
import com.ntg.sms.Entities.Student;
import com.ntg.sms.Mapper.AttendanceMapper;
import com.ntg.sms.Repositories.AttendanceRepository;
import com.ntg.sms.Repositories.SessionRepository;
import com.ntg.sms.Repositories.StudentRepository;
import lombok.RequiredArgsConstructor;
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
    private final SessionRepository sessionRepository;
    private final AttendanceMapper attendanceMapper;

    public AttendaceResponse createAttendance(AttendanceRequest request) {

        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Session session = null;

        if (request.getSessionId() != null) {
            session = sessionRepository.findById(request.getSessionId())
                    .orElseThrow(() -> new RuntimeException("Session not found"));
        }

        Attendance attendance = Attendance.builder()
                .student(student)
                .session(session)
                .status(request.getStatus())
                .dateTime(request.getDateTime())
                .build();

        attendance = attendanceRepository.save(attendance);

        return attendanceMapper.toResponse(attendance);
    }

    public AttendaceResponse updateAttendance(Long id, AttendanceRequest request) {

        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Attendance not found"));

        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Session session = null;

        if (request.getSessionId() != null) {
            session = sessionRepository.findById(request.getSessionId())
                    .orElseThrow(() -> new RuntimeException("Session not found"));
        }

        attendance.setStudent(student);
        attendance.setSession(session);
        attendance.setStatus(request.getStatus());
        attendance.setDateTime(request.getDateTime());

        attendance = attendanceRepository.save(attendance);

        return attendanceMapper.toResponse(attendance);
    }

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

    public List<AttendaceResponse> getAttendanceBySession(Long sessionId) {

        return attendanceRepository.findBySessionId(sessionId)
                .stream()
                .map(attendanceMapper::toResponse)
                .toList();
    }

    public void deleteAttendance(Long id) {

        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Attendance not found"));

        attendanceRepository.delete(attendance);
    }

    public Double getTodayAttendance() {
     LocalDate today = LocalDate.now();
      LocalDateTime start = today.atStartOfDay();
     LocalDateTime end   = today.plusDays(1).atStartOfDay();
      return (double) attendanceRepository.countByWeek(start, end);
  }

    public List<Long> getWeeklyAttendanceCounts(int weeks) {
        List<Long> result = new ArrayList<>();
        // find the start of the current week (Monday)
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


}