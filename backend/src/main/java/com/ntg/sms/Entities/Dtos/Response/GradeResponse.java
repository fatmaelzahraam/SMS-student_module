package com.ntg.sms.Entities.Dtos.Response;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Set;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GradeResponse {

    private Long id;
    private String gradename;
    private Set<TermSummary> terms;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TermSummary {
        private Long id;
        private String name;
    }
}
