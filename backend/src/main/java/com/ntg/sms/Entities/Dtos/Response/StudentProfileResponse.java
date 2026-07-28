package com.ntg.sms.Entities.Dtos.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentProfileResponse {

    private Long        studentId;
    private String      firstName;
    private String      lastName;
    private String      fullName;
    private String      email;
    private String      role;
    private String      className;
    private String      gradeName;
    private List<Long>  phoneNumbers;   // multiple numbers
    private Long        nationalId;
    private LocalDate   birthDate;
    private String      governorate;
    private String      placeOfBirth;
    private String      profileImage;
}