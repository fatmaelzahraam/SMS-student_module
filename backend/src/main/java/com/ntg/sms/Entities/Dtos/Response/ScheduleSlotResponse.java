package com.ntg.sms.Entities.Dtos.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleSlotResponse {
    private String timeSlot;              // e.g. "08:00 - 08:50"
    private ClassSessionResponse session;
}
