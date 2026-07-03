package com.ntg.sms.Repositories;

import com.ntg.sms.Entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationsReository extends JpaRepository<Notification,Long> {
}
