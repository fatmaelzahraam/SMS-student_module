package com.ntg.sms.Entities.Dtos.Response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class SessionResponse {

    private Long id;
    private Long classId;
    private String className;
    private Long courseId;
    private String courseName;
    private Long dayOfWeek;
    private LocalDate startAt;
    private LocalDate endAt;
    private LocalDate updatedAt;

}
