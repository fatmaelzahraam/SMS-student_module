package com.ntg.sms.Repositories;

import com.ntg.sms.Entities.Assignment;
import com.ntg.sms.Entities.Dtos.Response.CourseResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AssignmentRepository extends JpaRepository <Assignment,Long>{

    List<Assignment> findByCoursesId(Long courseId);

    @Query("""
SELECT new com.sms.dto.AssignmentSummary(

COUNT(sa),

SUM(
CASE
WHEN sa.status='COMPLETED'
THEN 1
ELSE 0
END
)

)

FROM StudentAssignment sa

WHERE sa.student.id=:studentId
""")
    CourseResponse.AssignmentSummary getAssignmentSummary(Long studentId);
}
