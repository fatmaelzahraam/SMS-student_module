package com.ntg.sms.Entities.Dtos.Request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SessionRequest {

    private Long id;
    private Long classId;
    private Long courseId;
    private Long dayOfWeek;
    private LocalDate startAt;
    private LocalDate endAt;
    private LocalDate updatedAt;

}
