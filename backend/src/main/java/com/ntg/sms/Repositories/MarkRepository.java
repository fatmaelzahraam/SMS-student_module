package com.ntg.sms.Repositories;

import com.ntg.sms.Entities.Mark;
import com.ntg.sms.Entities.Dtos.Response.SubjectAverageResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarkRepository extends JpaRepository<Mark, Long> {

    List<Mark> findByCourseId(Long courseId);

    @Query("""
            SELECT COALESCE(SUM(m.score),0)
            FROM Mark m
            WHERE m.student.id=:studentId
            AND m.course.id=:courseId
            AND m.isApproved=true
            """)
    Long sumApprovedScoreByUserAndCourse(
            @Param("studentId") Long studentId,
            @Param("courseId") Long courseId
    );

    @Query(value = """
            SELECT rank
            FROM(
            
                SELECT
                student_id,
                RANK() OVER(
                    ORDER BY SUM(score)/SUM(max_score) DESC
                ) rank
            
                FROM MARKS
                GROUP BY student_id
            
            )
            WHERE student_id=:studentId
            """,
            nativeQuery = true)
    Integer getStudentRank(@Param("studentId") Long studentId);

    @Query("""
            SELECT
            COALESCE(
                (SUM(m.score)*100.0)/SUM(m.maxScore),
                0
            )
            FROM Mark m
            WHERE m.student.id=:studentId
            """)
    Double calculatePerformance(@Param("studentId") Long studentId);

    @Query("""
            SELECT
            MAX(
                (m.score*100.0)/m.maxScore
            )
            FROM Mark m
            WHERE m.student.id=:studentId
            """)
    Double highestPercentage(@Param("studentId") Long studentId);

    @Query("""
            SELECT
            MIN(
                (m.score*100.0)/m.maxScore
            )
            FROM Mark m
            WHERE m.student.id=:studentId
            """)
    Double lowestPercentage(@Param("studentId") Long studentId);

    @Query("""
            SELECT
            COUNT(DISTINCT m.course.id)
            FROM Mark m
            WHERE m.student.id=:studentId
            """)
    Integer totalSubjects(@Param("studentId") Long studentId);

    @Query("""
            SELECT new com.ntg.sms.Entities.Dtos.Response.SubjectAverageResponse(
                m.course.courseName,
                (SUM(m.score)*100.0)/SUM(m.maxScore)
            )
            FROM Mark m
            WHERE m.student.id=:studentId
            GROUP BY m.course.courseName
            ORDER BY m.course.courseName
            """)
    List<SubjectAverageResponse> getSubjectAverages(
            @Param("studentId") Long studentId
    );

    @Query("""
        SELECT m
        FROM Mark m
        JOIN FETCH m.course c
        JOIN FETCH m.student s
        JOIN FETCH s.user u
        JOIN FETCH m.type t
        WHERE s.id=:studentId
        ORDER BY m.feedbackDate
        """)
    List<Mark> findByStudentId(
            @Param("studentId") Long studentId
    );

    @Query("""
        SELECT m
        FROM Mark m
        JOIN FETCH m.course c
        JOIN FETCH m.student s
        JOIN FETCH s.user u
        JOIN FETCH m.type t
        WHERE s.id=:studentId
        AND c.id=:courseId
        ORDER BY m.feedbackDate
        """)
    List<Mark> findByStudentIdAndCourseId(
            @Param("studentId") Long studentId,
            @Param("courseId") Long courseId
    );

}