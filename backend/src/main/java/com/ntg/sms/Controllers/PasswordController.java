package com.ntg.sms.Controllers;

import com.ntg.sms.Entities.Dtos.Request.ChangePasswordRequest;
import com.ntg.sms.Service.PasswordService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/password")
@RequiredArgsConstructor
public class PasswordController {

    private final PasswordService passwordService;

    @PreAuthorize("hasRole('STUDENT')")
    @PatchMapping("/change")
    public ResponseEntity<Map<String, String>> changePassword(
            HttpServletRequest request,
            @Valid @RequestBody ChangePasswordRequest body) {

        Long userId = (Long) request.getAttribute("userId");
        passwordService.changePassword(userId, body);

        return ResponseEntity.ok(Map.of("message", "Password changed successfully"));
    }
}
