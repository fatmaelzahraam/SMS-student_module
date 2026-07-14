package com.ntg.sms.Entities.Dtos.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassSessionResponse {
    private Long id;
    private String subject;      // course.courseName
    private String subjectCode;  // course.courseType
    private String teacher;
}