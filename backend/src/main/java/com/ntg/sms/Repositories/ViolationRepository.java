package com.ntg.sms.Repositories;

import com.ntg.sms.Entities.Violation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

public interface ViolationRepository extends JpaRepository<Violation, Long> {
    List<Violation> findByStudentId(Long studentId);

    List<Violation> findByDate(LocalDate date);

}
