package com.ntg.sms.Entities.Dtos.Response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ComplaintResponse {

    private Long complaintId;
    private String title;
    private String status;
    private LocalDateTime submittedAt;
}