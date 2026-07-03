package com.ntg.sms.Service;

import com.ntg.sms.Entities.Dtos.Request.ViolationRequest;
import com.ntg.sms.Entities.Dtos.Response.ViolationResponse;
import com.ntg.sms.Entities.Student;
import com.ntg.sms.Entities.Violation;
import com.ntg.sms.Mapper.ViolationMapper;
import com.ntg.sms.Repositories.StudentRepository;
import com.ntg.sms.Repositories.ViolationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ViolationService {

    private final ViolationRepository violationRepository;
    private final StudentRepository studentRepository;
    private final ViolationMapper violationMapper;

    public ViolationResponse createViolation(ViolationRequest request) {

        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Violation violation = new Violation();

        violation.setStudent(student);
        violation.setViolation(request.getViolation());
        violation.setNameOfViolator(request.getNameOfViolator());
        violation.setApplicableProcedure(request.getApplicableProcedure());
        violation.setReferringAuthority(request.getReferringAuthority());
        violation.setIsmeeting(request.getIsmeeting());
        violation.setNotes(request.getNotes());
        violation.setDate(request.getDate());

        return violationMapper.toResponse(
                violationRepository.save(violation));
    }

    public ViolationResponse updateViolation(Long id, ViolationRequest request) {

        Violation violation = violationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Violation not found"));

        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        violation.setStudent(student);
        violation.setViolation(request.getViolation());
        violation.setNameOfViolator(request.getNameOfViolator());
        violation.setApplicableProcedure(request.getApplicableProcedure());
        violation.setReferringAuthority(request.getReferringAuthority());
        violation.setIsmeeting(request.getIsmeeting());
        violation.setNotes(request.getNotes());
        violation.setDate(request.getDate());

        return violationMapper.toResponse(
                violationRepository.save(violation));
    }

    public ViolationResponse getViolationById(Long id) {

        Violation violation = violationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Violation not found"));

        return violationMapper.toResponse(violation);
    }

    public List<ViolationResponse> getAllViolations() {

        return violationRepository.findAll()
                .stream()
                .map(violationMapper::toResponse)
                .toList();
    }

    public List<ViolationResponse> getViolationsByStudent(Long studentId) {

        return violationRepository.findByStudentId(studentId)
                .stream()
                .map(violationMapper::toResponse)
                .toList();
    }

    public void deleteViolation(Long id) {

        Violation violation = violationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Violation not found"));

        violationRepository.delete(violation);
    }

}
