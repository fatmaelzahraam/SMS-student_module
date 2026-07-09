package com.ntg.sms.Mapper;

import java.time.LocalDate;

public interface StudentProfileMapper {
    Long getStudentId();

    String getFullName();

    String getEmail();

    Long getPhoneNumber();

    Long getNationalId();

    LocalDate getBirthDate();

    String getGovernorate();

    String getPlaceOfBirth();
}
