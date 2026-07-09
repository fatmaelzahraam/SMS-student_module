package com.ntg.sms.Entities.Dtos.Response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class ViolationDetailsResponse {

    private Long violationId;
    private String violation;
    private String notes;
    private String applicableProcedure;
    private String referringAuthority;
    private Long ismeeting;
    private LocalDate date;
}
