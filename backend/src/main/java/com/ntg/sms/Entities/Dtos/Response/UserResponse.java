package com.ntg.sms.Entities.Dtos.Response;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserResponse {
    Long id;
    @NotNull
    String firstName;
    @NotNull
    String lastName;
    @NotNull
    String email;
    String address;
    String firstNameInArabic;
    String lastNameInArabic;
    Boolean isDeleted;
    LocalDateTime createdAt;
    LocalDateTime lastLogin;
    Character gender;
    String nationality;
    LocalDate birthDate;
    String religion;
    Long nationalNumber;
}
