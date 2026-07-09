package com.ntg.sms.Repositories;

import com.ntg.sms.Entities.Class;
import com.ntg.sms.Entities.Dtos.Response.ClassResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassRepository extends JpaRepository<Class, Long> {

    List<Class> findByGradeId(Long gradeId);

    boolean existsByNameAndGradeId(String name, Long gradeId);
    @Query("""
        SELECT new com.ntg.sms.Entities.Dtos.Response.ClassResponse(
                                                                                  cl.id,
                                                                                  cl.name,
                                                                                  cl.grade.name
            
                                                                                  )FROM Class cl
""")
    List<ClassResponse> findAllClassRes();
}