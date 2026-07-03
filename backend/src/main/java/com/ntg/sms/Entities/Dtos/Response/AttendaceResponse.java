package com.ntg.sms.Entities.Dtos.Response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AttendaceResponse {
    private Long id;
    private Long studentId;
    private String studentName;
    private Long sessionId;
    private Character status;
    private LocalDateTime dateTime;
}
