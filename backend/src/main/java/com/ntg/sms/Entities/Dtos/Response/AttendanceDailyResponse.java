package com.ntg.sms.Entities.Dtos.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceDailyResponse {
    private LocalDate date;
    private long totalSessions;
    private long present;
    private long absent;
    private long late;
    private double attendancePercentage;
    private List<AttendaceResponse> attendanceList;
}
