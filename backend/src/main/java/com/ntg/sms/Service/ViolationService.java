package com.ntg.sms.Service;

import com.ntg.sms.Entities.Dtos.Response.ViolationDetailsResponse;
import com.ntg.sms.Entities.Dtos.Response.ViolationResponse;
import com.ntg.sms.Entities.Dtos.Response.ViolationStatisticsResponse;
import com.ntg.sms.Entities.Student;
import com.ntg.sms.Entities.User;
import com.ntg.sms.Entities.Violation;
import com.ntg.sms.Repositories.StudentRepository;
import com.ntg.sms.Repositories.ViolationRepository;
import com.ntg.sms.Security.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ViolationService {

    private final ViolationRepository violationRepository;
    private final StudentRepository studentRepository;
    private final AuthenticationService authenticationService;

    public List<ViolationResponse> getMyViolations() {

        User currentUser = authenticationService.getUser();

        Student student = studentRepository.findByUser(currentUser)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        return violationRepository.findByStudentOrderByDateDesc(student)
                .stream()
                .map(v -> ViolationResponse.builder()
                        .violationId(v.getId())
                        .violation(v.getViolation())
                        .date(v.getDate())
                        .build())
                .toList();
    }

    public ViolationDetailsResponse getViolationDetails(Long violationId) {

        User currentUser = authenticationService.getUser();

        Student student = studentRepository.findByUser(currentUser)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Violation violation = violationRepository
                .findByIdAndStudent(violationId, student)
                .orElseThrow(() -> new RuntimeException("Violation not found"));

        return ViolationDetailsResponse.builder()
                .violationId(violation.getId())
                .violation(violation.getViolation())
                .notes(violation.getNotes())
                .applicableProcedure(violation.getApplicableProcedure())
                .referringAuthority(violation.getReferringAuthority())
                .ismeeting(violation.getIsmeeting())
                .date(violation.getDate())
                .build();
    }

    public ViolationStatisticsResponse getStatistics() {

        User currentUser = authenticationService.getUser();

        Student student = studentRepository.findByUser(currentUser)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        LocalDate now = LocalDate.now();

        LocalDate startOfMonth = now.withDayOfMonth(1);

        LocalDate endOfMonth = now.withDayOfMonth(now.lengthOfMonth());

        long total = violationRepository.countByStudent(student);

        long thisMonth = violationRepository.countByStudentAndDateBetween(
                student,
                startOfMonth,
                endOfMonth
        );

        long guardianSummons =
                violationRepository.countByStudentAndIsmeetingTrue(student);

        return new ViolationStatisticsResponse(
                total,
                thisMonth,
                guardianSummons
        );
    }
}