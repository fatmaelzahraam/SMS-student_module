package com.ntg.sms.Entities.Dtos.Response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ViolationResponse {

    private Long id;
    private Long studentId;
    private String studentName;
    private String violation;
    private String nameOfViolator;
    private String applicableProcedure;
    private String referringAuthority;
    private Long ismeeting;
    private String notes;
    private LocalDate date;

}