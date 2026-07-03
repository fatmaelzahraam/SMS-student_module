package com.ntg.sms.Repositories;

import com.ntg.sms.Entities.Dtos.Response.MarkResponse;
import com.ntg.sms.Entities.Mark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MarkRepository extends JpaRepository<Mark, Long> {

    List<Mark> findByUserId(Long userId);

    List<Mark> findByCourseId(Long courseId);

    List<Mark> findByTypeId(Long typeId);

    List<Mark> findByUserIdAndCourseId(Long userId, Long courseId);

    List<Mark> findByIsApproved(Boolean isApproved);

    @Query("SELECT m FROM Mark m WHERE m.user.id = :userId AND m.isApproved = true")
    List<Mark> findApprovedMarksByUser(@Param("userId") Long userId);

    @Query("SELECT COALESCE(SUM(m.score), 0) FROM Mark m WHERE m.user.id = :userId AND m.course.id = :courseId AND m.isApproved = true")
    Long sumApprovedScoreByUserAndCourse(@Param("userId") Long userId, @Param("courseId") Long courseId);

    boolean existsByUserIdAndCourseIdAndTypeId(Long userId, Long courseId, Long typeId);

//    @Query("""
//SELECT new com.ntg.sms.Entities.Dtos.Response.MarkResponse(
//    s.student_id,
//    u.firstName,
//    SUM(m.score),
//    SUM(a.totalMark),
//    (SUM(m.score) * 100.0 / SUM(a.totalMark))
//)
//FROM Mark m
//JOIN m.student s
//JOIN s.user u
//JOIN m.assignment a
//WHERE s.student_id = :studentId
//GROUP BY s.student_id, u.firstName
//""")
//    Optional<MarkResponse> getStudentPerformance(Long studentId);
//
//    @Query("""
//SELECT COUNT(DISTINCT s2.student_id)
//FROM Mark m2
//JOIN m2.student s2
//JOIN s2.studentClass c2
//JOIN c2.grade g2
//WHERE g2.id = :gradeId
//GROUP BY s2.student_id
//HAVING SUM(m2.score) >
//(
//    SELECT SUM(m1.score)
//    FROM Mark m1
//    WHERE m1.student.student_id = :studentId
//)
//""")
//    Long countStudentsAbove(Long studentId, Long gradeId);

    @Query(value = """
SELECT rank
FROM (

SELECT

student_id,

RANK() OVER (
ORDER BY SUM(score)/SUM(max_score) DESC
) rank

FROM marks

GROUP BY student_id

)

WHERE student_id=:studentId
""",
            nativeQuery = true)
    Integer getStudentRank(Long studentId);

    @Query("""
SELECT
COALESCE((SUM(m.score) * 100.0) / SUM(m.maxScore),0)

FROM Mark m

WHERE m.student.id=:studentId
""")
    Double calculatePerformance(Long studentId);

}
