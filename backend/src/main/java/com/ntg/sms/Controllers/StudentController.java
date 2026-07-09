package com.ntg.sms.Controllers;

import com.ntg.sms.Entities.Dtos.Response.StudentProfileResponse;
import com.ntg.sms.Entities.Dtos.Response.StudentResponse;
import com.ntg.sms.Entities.Student;
import com.ntg.sms.Repositories.StudentRepository;
import com.ntg.sms.Service.StudentService;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
@Builder
public class StudentController {

    private final StudentRepository studentRepository;

    @GetMapping("/profile")
    @PreAuthorize("hasRole('STUDENT') or hasRole('ADMIN')")
    public ResponseEntity<StudentProfileResponse> getProfile() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        Student student = studentRepository.findByUser_Email(email)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        return ResponseEntity.ok(
                StudentProfileResponse.builder()
                        .studentId(student.getId())
                        .firstName(student.getUser().getFirstName())
                        .lastName(student.getUser().getLastName())
                        .fullName(student.getUser().getFirstName() + " " + student.getUser().getLastName())
                        .email(student.getUser().getEmail())
                        .role(student.getUser().getRole().getRoleName())
                        .className(student.getStudentClass() != null
                                ? student.getStudentClass().getName() : "")
                        .build()
        );
    }

}
