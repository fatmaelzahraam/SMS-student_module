package com.ntg.sms.Entities.Dtos.Request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AttendanceRequest {
    private Long studentId;
    private Long sessionId;
    private Character status;
    private LocalDateTime dateTime;
}
