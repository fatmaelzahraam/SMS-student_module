package com.ntg.sms.Controllers;


import com.ntg.sms.Entities.Dtos.Request.CreateComplaintRequest;
import com.ntg.sms.Entities.Dtos.Response.ComplaintDetailsResponse;
import com.ntg.sms.Entities.Dtos.Response.ComplaintResponse;
import com.ntg.sms.Entities.Dtos.Response.ComplaintStatisticsResponse;
import com.ntg.sms.Service.ComplaintsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student/complaints")
@RequiredArgsConstructor
public class ComplaintsController {

    private final ComplaintsService complaintsService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createComplaint(@Valid @RequestBody CreateComplaintRequest request) {
        complaintsService.createComplaint(request);
    }

    @GetMapping
    public List<ComplaintResponse> getMyComplaints() {
        return complaintsService.getMyComplaints();
    }

    @GetMapping("/{complaintId}")
    public ComplaintDetailsResponse getComplaintDetails(@PathVariable Long complaintId) {
        return complaintsService.getComplaintDetails(complaintId);
    }

    @GetMapping("/statistics")
    public ComplaintStatisticsResponse getStatistics() {
        return complaintsService.getStatistics();
    }
}