package com.ntg.sms.Entities.Dtos.Request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ViolationRequest {

    private Long studentId;
    private String violation;
    private String nameOfViolator;
    private String applicableProcedure;
    private String referringAuthority;
    private Long ismeeting;
    private String notes;
    private LocalDate date;

}
