package com.ntg.sms.Repositories;

import com.ntg.sms.Entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query("SELECT DISTINCT c FROM Course c LEFT JOIN FETCH c.assignments LEFT JOIN FETCH c.teacher t LEFT JOIN FETCH t.user LEFT JOIN FETCH c.term")
    List<Course> findAllWithDetails();

    List<Course> findByTeacherId(Long teacherId);

    List<Course> findByTermId(Long termId);

    List<Course> findByCourseType(String courseType);

    @Query("SELECT c FROM Course c LEFT JOIN FETCH c.assignments WHERE c.id = :id")
    Optional<Course> findByIdWithAssignments(@Param("id") Long id);
}