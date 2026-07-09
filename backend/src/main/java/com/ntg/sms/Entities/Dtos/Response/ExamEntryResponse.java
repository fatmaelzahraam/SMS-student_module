package com.ntg.sms.Entities.Dtos.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExamEntryResponse {
    private Long id;
    private String subject;    // course.courseName
    private String teacher;    // teacher name
    private String startTime;  // "08:00"
    private String endTime;    // "09:00"
    private String type;       // "MONTH_EXAM" or "FINAL_EXAM"
}