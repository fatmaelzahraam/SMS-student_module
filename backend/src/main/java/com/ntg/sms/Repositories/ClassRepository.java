package com.ntg.sms.Repositories;

import com.ntg.sms.Entities.Class;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassRepository extends JpaRepository<Class, Long> {

    List<Class> findByGradeId(Long gradeId);

    boolean existsByNameAndGradeId(String name, Long gradeId);
}