package com.ntg.sms.Entities.Dtos.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceOverviewResponse {
    private long totalDays;
    private long presentDays;
    private long absentDays;
    private long lateDays;
    private double attendancePercentage;
}
