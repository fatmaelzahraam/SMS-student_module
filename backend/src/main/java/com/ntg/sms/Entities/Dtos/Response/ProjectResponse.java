package com.ntg.sms.Entities.Dtos.Response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ProjectResponse {

    private Long id;
    private Long courseId;
    private String courseName;
    private String name;
    private String description;
    private LocalDate assignDate;
    private LocalDate deadline;

}
