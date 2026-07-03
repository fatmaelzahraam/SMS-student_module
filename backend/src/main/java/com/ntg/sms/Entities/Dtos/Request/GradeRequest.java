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
public class GradeRequest {

    @NotNull(message = "Grade ID is required")
    private Long id;

    @NotBlank(message = "Grade name is required")
    @Size(max = 100, message = "Grade name must not exceed 100 characters")
    private String name;

    // Optional: link existing terms at creation/update
    private Set<Long> termIds;
}
