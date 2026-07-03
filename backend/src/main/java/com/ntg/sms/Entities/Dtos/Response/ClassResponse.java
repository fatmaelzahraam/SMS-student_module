package com.ntg.sms.Entities.Dtos.Response;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class ClassResponse {
    Long id;
    @NotNull
    GradeResponse grade;
    @NotNull
    String name;
    @NotNull
    Long capacity;
    // Flattened grade fields — avoids lazy-loading issues
    private Long gradeId;
    private String gradeName;
}
