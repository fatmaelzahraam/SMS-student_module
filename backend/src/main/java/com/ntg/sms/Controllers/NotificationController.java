package com.ntg.sms.Controllers;

import com.ntg.sms.Entities.Dtos.Response.NotificationResponse;
import com.ntg.sms.Service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping
    @PreAuthorize("hasRole('STUDENT') or hasRole('ADMIN')")
    public List<NotificationResponse> getUserNotifications(
            @AuthenticationPrincipal UserDetails userDetails) {

        String email = userDetails.getUsername();
        return notificationService.getUserNotificationsByEmail(email);
    }
}