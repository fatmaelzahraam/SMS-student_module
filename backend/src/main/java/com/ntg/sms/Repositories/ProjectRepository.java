package com.ntg.sms.Repositories;

import com.ntg.sms.Entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findByCourseId(Long courseId);

    List<Project> findByDeadline(LocalDate deadline);

}
