package com.ntg.sms.Repositories;

import com.ntg.sms.Entities.Attendance;
import com.ntg.sms.Entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    List<Attendance> findByStudentId(Long studentId);
    long countByStudent(Student student);
    long countByStudentAndStatus(Student student, Character status);
    List<Attendance> findBySessionId(Long sessionId);
    List<Attendance> findByStudent(Student student);


    @Query("""
                SELECT COUNT(a)
                FROM Attendance a
                WHERE a.dateTime >= :startOfDay
                  AND a.dateTime < :startOfNextDay
            """)
    long countByWeek(

            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("startOfNextDay") LocalDateTime startOfNextDay
    );

    default long countToday() {
        LocalDate today = LocalDate.now();

        return countByWeek(
                today.atStartOfDay(),
                today.plusDays(1).atStartOfDay()
        );
    }
    // student in specfice day
    @Query("""
       SELECT a
       FROM Attendance a
       WHERE a.student = :student
       AND a.dateTime >= :startOfDay
       AND a.dateTime < :endOfDay
       ORDER BY a.dateTime
       """)
    List<Attendance> findDailyAttendance(
            @Param("student") Student student,
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("endOfDay") LocalDateTime endOfDay
    );
    // number of persent in  specfic day
    @Query("""
       SELECT COUNT(a)
       FROM Attendance a
       WHERE a.student = :student
       AND a.status = 'P'
       AND a.dateTime >= :startOfDay
       AND a.dateTime < :endOfDay
       """)
    long countPresentDaily(
            @Param("student") Student student,
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("endOfDay") LocalDateTime endOfDay
    );
    //number of Apsent in specfic day
    @Query("""
       SELECT COUNT(a)
       FROM Attendance a
       WHERE a.student = :student
       AND a.status = 'A'
       AND a.dateTime >= :startOfDay
       AND a.dateTime < :endOfDay
       """)
    long countAbsentDaily(
            @Param("student") Student student,
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("endOfDay") LocalDateTime endOfDay
    );
  // number opf late in day
    @Query("""
       SELECT COUNT(a)
       FROM Attendance a
       WHERE a.student = :student
       AND a.status = 'L'
       AND a.dateTime >= :startOfDay
       AND a.dateTime < :endOfDay
       """)
    long countLateDaily(
            @Param("student") Student student,
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("endOfDay") LocalDateTime endOfDay
    );


}
