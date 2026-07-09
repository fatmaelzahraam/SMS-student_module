package com.ntg.sms.Repositories;

import com.ntg.sms.Entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query("SELECT p FROM Project p LEFT JOIN FETCH p.course")
    List<Project> findAllWithCourse();

    @Query("SELECT p FROM Project p LEFT JOIN FETCH p.course WHERE p.course.id = :courseId")
    List<Project> findByCourseIdWithCourse(@Param("courseId") Long courseId);

    List<Project> findByCourseId(Long courseId);

}