package com.ntg.sms.Mapper;

import java.time.LocalDateTime;

public interface StudentProfileMapper {

    Long getStudentId();
    Long getUserId();
    String getFirstName();
    String getLastName();
    String getFullName();
    String getEmail();
    String getRole();
    Long getNationalId();
    LocalDateTime getBirthDate();
    String getGovernorate();
    String getPlaceOfBirth();
    String getClassName();
    String getGradeName();
}