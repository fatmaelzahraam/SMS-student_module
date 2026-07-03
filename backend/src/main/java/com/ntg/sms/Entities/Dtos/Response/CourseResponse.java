package com.ntg.sms.Entities.Dtos.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseResponse {

    private Long id;
    private String courseName;
    private String courseType;
    private String description;
    private String studyPlan;

    private Long teacherId;
    private String teacherName;     // firstName + lastName

    private Long termId;
    private Long termNumber;        // term.term
    private Long termYear;          // term.year

    private Set<AssignmentSummary> assignments;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AssignmentSummary {
        private Long id;
        private String name;        // assignment.name
    }
}