package com.ntg.sms.Entities.Dtos.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceMonthlyResponse {
    private int month;
    private int year;
    private long present;
    private long absent;
    private long late;
    private double attendancePercentage;
}
