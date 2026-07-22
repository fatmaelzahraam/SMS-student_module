package com.ntg.sms.Repositories;

import com.ntg.sms.Entities.Student;
import com.ntg.sms.Entities.Violation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ViolationRepository extends JpaRepository<Violation, Long> {
    List<Violation> findByStudentId(Long studentId);

    List<Violation> findByDate(LocalDate date);
    List<Violation> findByStudentOrderByDateDesc(Student student);

    Optional<Violation> findByIdAndStudent(Long id, Student student);

    long countByStudent(Student student);

    long countByStudentAndDateBetween(
            Student student,
            LocalDate start,
            LocalDate end
    );

    @Query("SELECT COUNT(v) FROM Violation v WHERE v.student = :student AND v.ismeeting = 1")
    long countByStudentAndIsmeeting(@Param("student") Student student);

}
