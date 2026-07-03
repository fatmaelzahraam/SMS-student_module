package com.ntg.sms.Entities.Dtos.Response;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class StudentResponse {

    Long id;
    @NotNull
    UserResponse user;
    String governorate;
    Long academicScoreInMiddleSchool;
    String placeOfBirth;
    ClassResponse studentClass;
    List<String> medicalHistory;
}
