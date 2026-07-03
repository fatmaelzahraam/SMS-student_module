package com.ntg.sms.Entities.Dtos.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MarkResponse {

    private Long id;
    private Boolean isApproved;
    private Long score;
    private Long maxScore;
    private Long courseId;
    private String courseName;
    private Long userId;
    private String userName;        // firstName + lastName
    private Long typeId;
    private String typeName; // from type.type

}