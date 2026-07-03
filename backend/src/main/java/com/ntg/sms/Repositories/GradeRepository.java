package com.ntg.sms.Repositories;

import com.ntg.sms.Entities.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {

    boolean existsByName(String name);

    List<Grade> findByTermsId(Long termId);

    @Query("SELECT g FROM Grade g LEFT JOIN FETCH g.terms WHERE g.id = :id")
    Optional<Grade> findByIdWithTerms(@Param("id") Long id);
}