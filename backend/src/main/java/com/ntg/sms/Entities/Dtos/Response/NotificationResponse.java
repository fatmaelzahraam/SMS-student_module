package com.ntg.sms.Entities.Dtos.Response;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@Data
public class NotificationResponse {
    private Long id;
    private String title;
    private String body;
    private String type;
    private String priority;
    private LocalDate sentAt;
}