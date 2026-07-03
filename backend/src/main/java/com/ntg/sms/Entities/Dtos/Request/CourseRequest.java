package com.ntg.sms.Entities.Dtos.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseRequest {

    @NotNull(message = "Course ID is required")
    private Long id;

    @NotNull(message = "Teacher ID is required")
    private Long teacherId;

    @NotNull(message = "Term ID is required")
    private Long termId;

    @NotBlank(message = "Course type is required")
    @Size(max = 80, message = "Course type must not exceed 80 characters")
    private String courseType;

    @NotBlank(message = "Course name is required")
    @Size(max = 100, message = "Course name must not exceed 100 characters")
    private String courseName;

    @Size(max = 100, message = "Description must not exceed 100 characters")
    private String description;

    @Size(max = 100, message = "Study plan must not exceed 100 characters")
    private String studyPlan;

    private Set<Long> assignmentIds;
}