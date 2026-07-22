package com.ntg.sms.Entities.Dtos.Response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class ViolationResponse {
    private Long violationId;
    private String violation;
    private LocalDate date;
    private boolean isMeeting;
}