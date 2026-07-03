package com.ntg.sms.Entities.Dtos.Request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ProjectRequest {

    private Long id;

    private Long courseId;

    private String name;

    private String description;

    private LocalDate assignDate;

    private LocalDate deadline;

}
