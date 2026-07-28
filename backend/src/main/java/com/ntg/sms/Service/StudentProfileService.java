package com.ntg.sms.Service;

import com.ntg.sms.Entities.Dtos.Response.StudentProfileResponse;
import com.ntg.sms.Mapper.StudentProfileMapper;
import com.ntg.sms.Repositories.StudentProfileRepository;
import com.ntg.sms.Repositories.UserPhoneNumberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudentProfileService {

    private final StudentProfileRepository    repository;
    private final UserPhoneNumberRepository   userPhoneNumberRepository;

    // ── Called endpoint: /profile/{studentId} ───────────────────────
    public StudentProfileResponse getProfile(Long studentId) {
        StudentProfileMapper row = repository.getStudentProfile(studentId);
        return mapToResponse(row, "id: " + studentId);
    }

    // ── Called by student endpoint: /profile (JWT email) ─────────────────────
    public StudentProfileResponse getProfileByEmail(String email) {
        StudentProfileMapper row = repository.getStudentProfileByEmail(email);
        return mapToResponse(row, "email: " + email);
    }


    private StudentProfileResponse mapToResponse(StudentProfileMapper row, String identifier) {
        if (row == null) {
            throw new EntityNotFoundException("Student profile not found for " + identifier);
        }

        List<Long> phones = userPhoneNumberRepository
                .findById_UserId(row.getUserId())
                .stream()
                .map(upn -> upn.getId().getPhoneNumber())
                .toList();

        return StudentProfileResponse.builder()
                .studentId(row.getStudentId())
                .firstName(row.getFirstName())
                .lastName(row.getLastName())
                .fullName(row.getFullName())
                .email(row.getEmail())
                .role(row.getRole())
                .phoneNumbers(phones)
                .nationalId(row.getNationalId())
                .birthDate(row.getBirthDate() != null ? row.getBirthDate().toLocalDate() : null)
                .governorate(row.getGovernorate())
                .placeOfBirth(row.getPlaceOfBirth())
                .className(row.getClassName())
                .gradeName(row.getGradeName())
                .profileImage(null)
                .build();
    }

//    private LocalDate parseBirthDate(Object raw) {
//        if (raw == null) return null;
//        if (raw instanceof LocalDate ld)         return ld;
//        if (raw instanceof LocalDateTime ldt)    return ldt.toLocalDate();
//        if (raw instanceof Date sqlDate)         return sqlDate.toLocalDate();
//        if (raw instanceof java.sql.Timestamp ts) return ts.toLocalDateTime().toLocalDate();
//        throw new IllegalArgumentException("Unexpected birthDate type: " + raw.getClass().getName());
//    }
}