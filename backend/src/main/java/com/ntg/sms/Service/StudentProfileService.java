package com.ntg.sms.Service;

import com.ntg.sms.Entities.Dtos.Response.StudentProfileResponse;
import com.ntg.sms.Repositories.StudentProfileRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudentProfileService {

    private final StudentProfileRepository repository;

    // ── Called by admin endpoint: /profile/{studentId} ───────────────────────
    public StudentProfileResponse getProfile(Long studentId) {
        Map<String, Object> row = (Map<String, Object>) repository.getStudentProfile(studentId);
        return mapToResponse(row, "id: " + studentId);
    }

    // ── Called by student endpoint: /profile (JWT email)
    public StudentProfileResponse getProfileByEmail(String email) {
        Map<String, Object> row = (Map<String, Object>) repository.getStudentProfileByEmail(email);
        return mapToResponse(row, "email: " + email);
    }

    // ── Shared mapping logic
    private StudentProfileResponse mapToResponse(Map<String, Object> row, String identifier) {
        if (row == null) {
            throw new EntityNotFoundException("Student profile not found for " + identifier);
        }

        return StudentProfileResponse.builder()
                .studentId(((Number) row.get("studentId")).longValue())
                .fullName((String) row.get("fullName"))
                .email((String) row.get("email"))
                .phoneNumber(row.get("phoneNumber") == null ? null : ((Number) row.get("phoneNumber")).longValue())
                .nationalId(row.get("nationalId")   == null ? null : ((Number) row.get("nationalId")).longValue())
                .birthDate(parseBirthDate(row.get("birthDate")))
                .governorate((String) row.get("governorate"))
                .placeOfBirth((String) row.get("placeOfBirth"))
                .className((String) row.get("className"))
                .gradeName((String) row.get("gradeName"))
                .profileImage((String) row.get("profileImage"))
                .build();
    }
    private LocalDate parseBirthDate(Object raw) {
        if (raw == null) return null;
        if (raw instanceof LocalDate ld)       return ld;
        if (raw instanceof LocalDateTime ldt)  return ldt.toLocalDate();
        if (raw instanceof Date sqlDate)       return sqlDate.toLocalDate();
        if (raw instanceof java.sql.Timestamp ts) return ts.toLocalDateTime().toLocalDate();
        throw new IllegalArgumentException("Unexpected birthDate type: " + raw.getClass().getName());
    }
}