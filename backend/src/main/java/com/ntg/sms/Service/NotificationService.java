package com.ntg.sms.Service;

import com.ntg.sms.Entities.Dtos.Response.NotificationResponse;
import com.ntg.sms.Entities.UserNotification;
import com.ntg.sms.Mapper.NotificationMapper;
import com.ntg.sms.Repositories.UserNotificationRepository;
import com.ntg.sms.Repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationService {

    private final UserNotificationRepository userNotificationRepository;
    private final NotificationMapper notificationMapper;
    private final UserRepository userRepository;

    // Called by admin endpoint with explicit userId
    public List<NotificationResponse> getUserNotifications(Long userId) {
        List<UserNotification> notifications =
                userNotificationRepository.findBySentToId(userId);

        return notifications.stream()
                .map(UserNotification::getNotification)
                .map(notificationMapper::toResponse)
                .toList();
    }

    // Called by student endpoint — resolves userId from email via JWT principal
    public List<NotificationResponse> getUserNotificationsByEmail(String email) {
        Long userId = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + email))
                .getId();

        return getUserNotifications(userId);
    }
}