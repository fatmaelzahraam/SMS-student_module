package com.ntg.sms.Entities.Dtos.Request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MarkRequest {

    @NotNull(message = "Mark ID is required")
    private Long id;

    @NotNull(message = "Course ID is required")
    private Long courseId;

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Mark type ID is required")
    private Long typeId;

    @NotNull(message = "Feedback date is required")
    private LocalDate feedbackDate;

    @Size(max = 255, message = "Feedback must not exceed 255 characters")
    private String feedback;

    @Size(max = 255, message = "Notes must not exceed 255 characters")
    private String notes;

    private Boolean isApproved;

    @NotNull(message = "Score is required")
    @Min(value = 0, message = "Score must be zero or greater")
    private Long score;

    @NotNull(message = "Max score is required")
    @Min(value = 1, message = "Max score must be at least 1")
    private Long maxScore;
}