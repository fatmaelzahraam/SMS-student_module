package com.ntg.sms.Repositories;

import com.ntg.sms.Entities.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository

public interface PermissionRepository extends JpaRepository<Permission, Long> {

    List<Permission> findByStudentId(Long studentId);

    List<Permission> findByDateBetween(LocalDate from, LocalDate to);

    List<Permission> findByStudentIdAndDateBetween(Long studentId, LocalDate from, LocalDate to);
}
