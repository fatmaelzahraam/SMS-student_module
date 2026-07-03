package com.ntg.sms.Entities.Dtos.Request;


import jakarta.validation.constraints.NotBlank;
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
public class PermissionRequest {

    @NotNull(message = "Student ID is required")
    private Long studentId;

    @NotBlank(message = "Reason is required")
    @Size(max = 255, message = "Reason must not exceed 255 characters")
    private String reason;

    @Size(max = 255, message = "Notes must not exceed 255 characters")
    private String notes;

    // Optional: defaults to sysdate on DB side if not provided
    private LocalDate date;
}
