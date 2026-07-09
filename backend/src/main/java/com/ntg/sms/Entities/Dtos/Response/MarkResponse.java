package com.ntg.sms.Entities.Dtos.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MarkResponse {

    private Long      id;
    private Long      courseId;
    private String    courseName;
    private Long      studentId;
    private String    studentName;
    private Long      typeId;
    private String    typeName;
    private LocalDate feedbackDate;   //  frontend uses this for the line chart
    private String    feedback;
    private String    notes;
    private Boolean   isApproved;
    private Long      score;
    private Long      maxScore;
}