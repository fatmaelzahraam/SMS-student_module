package com.ntg.sms.Entities.Dtos.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttendaceResponse {
    private Long          id;
    private Long          studentId;
    private String        studentName;
    private Long          sessionId;
    private int           sessionNumber;
    private Character     status;
    private LocalDateTime dateTime;
    private String        courseName;
    private String        teacherName;
}