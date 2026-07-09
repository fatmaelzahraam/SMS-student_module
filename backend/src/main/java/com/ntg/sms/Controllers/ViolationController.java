package com.ntg.sms.Controllers;


import com.ntg.sms.Entities.Dtos.Response.ViolationDetailsResponse;
import com.ntg.sms.Entities.Dtos.Response.ViolationResponse;
import com.ntg.sms.Entities.Dtos.Response.ViolationStatisticsResponse;
import com.ntg.sms.Service.ViolationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/student/violations")
@RequiredArgsConstructor
public class ViolationController {

    private final ViolationService violationService;

    @GetMapping
    @PreAuthorize("hasRole('STUDENT')")
    public List<ViolationResponse> getMyViolations() {
        return violationService.getMyViolations();
    }

    @GetMapping("/{violationId}")
    @PreAuthorize("hasRole('STUDENT')")
    public ViolationDetailsResponse getViolationDetails(@PathVariable Long violationId) {
        return violationService.getViolationDetails(violationId);
    }

    @GetMapping("/statistics")
    @PreAuthorize("hasRole('STUDENT')")
    public ViolationStatisticsResponse getStatistics() {
        return violationService.getStatistics();
    }
}