package com.ntg.sms.Entities.Dtos.Response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
public class AssignmentResponse {
    private int completed;
    private int total;
    private int completionPercent;
    private Long id;
    private String name;
    private LocalDate deadline;
    private LocalDate assignDate;
    private String description;
    private String fileLink;
    private String studentSubmission;
    private Set<Long> courseIds;

}
