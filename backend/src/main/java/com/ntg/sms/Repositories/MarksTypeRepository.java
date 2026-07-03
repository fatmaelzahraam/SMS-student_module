package com.ntg.sms.Repositories;

import com.ntg.sms.Entities.MarksType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarksTypeRepository extends JpaRepository<MarksType, Long> {

    boolean existsByType(String type);
}