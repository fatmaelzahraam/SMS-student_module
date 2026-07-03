package com.ntg.sms.Entities.Dtos.Response;

import lombok.*;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class AuthenticationResponse {
    private String token;
    private Long expiresAt;
    private String role;

    @Data
    @Builder
    public static class PerformanceResponse {
        private int    score;
        private String label;
    }
}