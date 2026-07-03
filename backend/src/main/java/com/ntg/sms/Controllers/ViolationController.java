package com.ntg.sms.Controllers;

import com.ntg.sms.Entities.Dtos.Request.ViolationRequest;
import com.ntg.sms.Entities.Dtos.Response.ViolationResponse;
import com.ntg.sms.Service.ViolationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/Support/violations")
@RequiredArgsConstructor
public class ViolationController {

    private final ViolationService violationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ViolationResponse createViolation(@RequestBody ViolationRequest request) {
        return violationService.createViolation(request);
    }

    @PutMapping("/{id}")
    public ViolationResponse updateViolation(
            @PathVariable Long id,
            @RequestBody ViolationRequest request) {

        return violationService.updateViolation(id, request);
    }

    @GetMapping("/{id}")
    public ViolationResponse getViolationById(@PathVariable Long id) {
        return violationService.getViolationById(id);
    }

    @GetMapping
    public List<ViolationResponse> getAllViolations() {
        return violationService.getAllViolations();
    }

    @GetMapping("/student/{studentId}")
    public List<ViolationResponse> getViolationsByStudent(
            @PathVariable Long studentId) {

        return violationService.getViolationsByStudent(studentId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteViolation(@PathVariable Long id) {
        violationService.deleteViolation(id);
    }

}
