package com.ntg.sms.Mapper;

import com.ntg.sms.Entities.Dtos.Response.NotificationResponse;
import com.ntg.sms.Entities.Notification;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

    NotificationResponse toResponse(Notification notification);

    List<NotificationResponse> toResponse(List<Notification> notifications);
}
