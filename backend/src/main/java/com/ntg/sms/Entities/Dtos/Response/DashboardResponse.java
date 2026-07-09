package com.ntg.sms.Entities.Dtos.Response;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import lombok.Builder;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class DashboardResponse {
    private int   performanceScore;
    private String  performanceLabel;
    private Integer rank;
    private Long totalStudents;
    private DashboardAttendanceOverView attendance;
}
