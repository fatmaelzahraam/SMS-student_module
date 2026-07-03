package com.ntg.sms.Entities.Dtos.Request;


import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class AssignmentRequest {

    private String name;
    private LocalDate deadline;
    private LocalDate assignDate;
    private String description;
    private String fileLink;
    private String studentSubmission;
    private Set<Long> courseIds;
}
