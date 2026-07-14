package com.ntg.sms.Repositories;

import com.ntg.sms.Entities.Dtos.Response.SessionResponse;
import com.ntg.sms.Entities.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {

    @Query("""
    SELECT new com.ntg.sms.Entities.Dtos.Response.SessionResponse(
        s.id,
        s.classField.name,
        s.course.courseName,
        CONCAT(s.course.teacher.user.firstName, ' ', s.course.teacher.user.lastName),
        s.dayOfWeek,
        s.startAt,
        s.endAt,
        s.updatedAt
    )
    FROM Session s
    WHERE s.classField.id = :classId
""")
    List<SessionResponse> findAllSessionsByClassId(@Param("classId") Long classId);

    @Query("""
    SELECT new com.ntg.sms.Entities.Dtos.Response.SessionResponse(
        s.id,
        s.classField.name,
        s.course.courseName,
        CONCAT(s.course.teacher.user.firstName, ' ', s.course.teacher.user.lastName),
        s.dayOfWeek,
        s.startAt,
        s.endAt,
        s.updatedAt
    )
    FROM Session s
    JOIN Student st ON st.studentClass.id = s.classField.id
    WHERE st.id = :studentId
    AND s.sessionType = com.ntg.sms.Entities.Session$SessionType.CLASS
    ORDER BY s.dayOfWeek, s.startAt
""")
    List<SessionResponse> findClassSessionsByStudentId(@Param("studentId") Long studentId);

    @Query("""
    SELECT new com.ntg.sms.Entities.Dtos.Response.SessionResponse(
        s.id,
        s.classField.name,
        s.course.courseName,
        CONCAT(s.course.teacher.user.firstName, ' ', s.course.teacher.user.lastName),
        s.dayOfWeek,
        s.startAt,
        s.endAt,
        s.updatedAt
    )
    FROM Session s
    JOIN Student st ON st.studentClass.id = s.classField.id
    WHERE st.id = :studentId
    AND s.sessionType = com.ntg.sms.Entities.Session$SessionType.MONTH_EXAM
    ORDER BY s.updatedAt, s.startAt
""")
    List<SessionResponse> findMonthExamsByStudentId(@Param("studentId") Long studentId);

    @Query("""
    SELECT new com.ntg.sms.Entities.Dtos.Response.SessionResponse(
        s.id,
        s.classField.name,
        s.course.courseName,
        CONCAT(s.course.teacher.user.firstName, ' ', s.course.teacher.user.lastName),
        s.dayOfWeek,
        s.startAt,
        s.endAt,
        s.updatedAt
    )
    FROM Session s
    JOIN Student st ON st.studentClass.id = s.classField.id
    WHERE st.id = :studentId
    AND s.sessionType = com.ntg.sms.Entities.Session$SessionType.FINAL_EXAM
    ORDER BY s.updatedAt, s.startAt
""")
    List<SessionResponse> findFinalExamsByStudentId(@Param("studentId") Long studentId);
}