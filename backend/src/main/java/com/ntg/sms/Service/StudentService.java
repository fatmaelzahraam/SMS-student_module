package com.ntg.sms.Service;


import com.ntg.sms.Entities.Dtos.Response.StudentResponse;
import com.ntg.sms.Entities.Student;
import com.ntg.sms.Mapper.StudentMapper;
import com.ntg.sms.Repositories.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class StudentService {

    private final StudentRepository studentRepository;
        private final StudentMapper studentMapper;

        public StudentResponse getMyProfile(String email) {
            Student student = studentRepository.findByUser_Email(email)
                    .orElseThrow(() -> new EntityNotFoundException("Student not found"));
            return studentMapper.toDto(student);
        }
    }

