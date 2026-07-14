package com.ntg.sms.Entities.Dtos.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SessionResponse {

    private Long id;
    private String className;
    private String courseName;
    private String teacherName;
    private Long dayOfWeek;
    private LocalTime startAt;
    private LocalTime endAt;
    private LocalDate examDate;

}