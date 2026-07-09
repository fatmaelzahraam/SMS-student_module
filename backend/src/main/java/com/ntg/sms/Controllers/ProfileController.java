package com.ntg.sms.Controllers;

import com.ntg.sms.Entities.Dtos.Response.StudentProfileResponse;
import com.ntg.sms.Service.StudentProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final StudentProfileService service;

    @GetMapping
    public ResponseEntity<?> getMyProfile(@AuthenticationPrincipal UserDetails userDetails) {
        try {
            String email = userDetails.getUsername();
            System.out.println(">>> Profile requested for email: " + email);
            StudentProfileResponse response = service.getProfileByEmail(email);
            System.out.println(">>> Profile result: " + response);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.err.println(">>> Profile 500 cause: " + e.getClass().getSimpleName() + ": " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<?> getProfile(@PathVariable Long studentId) {
        try {
            return ResponseEntity.ok(service.getProfile(studentId));
        } catch (Exception e) {
            System.err.println(">>> Profile/{id} 500 cause: " + e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}