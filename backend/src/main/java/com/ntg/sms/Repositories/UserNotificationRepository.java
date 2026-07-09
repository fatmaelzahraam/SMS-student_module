package com.ntg.sms.Repositories;

import com.ntg.sms.Entities.UserNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserNotificationRepository extends JpaRepository<UserNotification,Long> {
    @Query("SELECT un FROM UserNotification un JOIN FETCH un.notification WHERE un.sentTo.id = :sentToId")
    List<UserNotification> findBySentToId(@Param("sentToId") Long sentToId);
}
