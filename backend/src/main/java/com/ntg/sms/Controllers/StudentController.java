package com.ntg.sms.Controllers;

import com.ntg.sms.Entities.Dtos.Response.StudentResponse;
import com.ntg.sms.Service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor

public class StudentController {
    private final StudentService studentService;

    @GetMapping("/profile")
    public ResponseEntity<StudentResponse> getMyProfile(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(
                studentService.getMyProfile(userDetails.getUsername())
        );
    }

}
