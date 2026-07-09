package com.ntg.sms.Repositories;

import com.ntg.sms.Entities.Student;
import com.ntg.sms.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface StudentRepository extends JpaRepository<Student,Long> {

    Optional<Student> findByUser_Email(String email);
    Optional<Student> findByUserId(Long userId);
    Optional<Student> findByUser(User user);
}
