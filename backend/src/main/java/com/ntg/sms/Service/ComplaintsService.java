package com.ntg.sms.Service;

import com.ntg.sms.Entities.Complaints;
import com.ntg.sms.Entities.Dtos.Request.CreateComplaintRequest;
import com.ntg.sms.Entities.Dtos.Response.ComplaintDetailsResponse;
import com.ntg.sms.Entities.Dtos.Response.ComplaintResponse;
import com.ntg.sms.Entities.Dtos.Response.ComplaintStatisticsResponse;
import com.ntg.sms.Entities.User;
import com.ntg.sms.Repositories.ComplaintsRepository;
import com.ntg.sms.Security.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ComplaintsService {

    private final ComplaintsRepository complaintsRepository;
    private final AuthenticationService authenticationService;

    public void createComplaint(CreateComplaintRequest request) {

        User currentUser = authenticationService.getUser();

        Complaints complaint = Complaints.builder()
                .user(currentUser)
                .title(request.getTitle())
                .description(request.getDescription())
                .status("PENDING")
                .response(null)
                .submittedAt(LocalDateTime.now())
                .build();

        complaintsRepository.save(complaint);
    }

    public List<ComplaintResponse> getMyComplaints() {

        User currentUser = authenticationService.getUser();

        return complaintsRepository.findByUserOrderBySubmittedAtDesc(currentUser)
                .stream()
                .map(complaint -> ComplaintResponse.builder()
                        .complaintId(complaint.getComplaintId())
                        .title(complaint.getTitle())
                        .status(complaint.getStatus())
                        .submittedAt(complaint.getSubmittedAt())
                        .build())
                .toList();
    }

    public ComplaintDetailsResponse getComplaintDetails(Long complaintId) {

        User currentUser = authenticationService.getUser();

        Complaints complaint = complaintsRepository
                .findByComplaintIdAndUser(complaintId, currentUser)
                .orElseThrow(() -> new RuntimeException("Complaint not found"));

        return ComplaintDetailsResponse.builder()
                .complaintId(complaint.getComplaintId())
                .title(complaint.getTitle())
                .description(complaint.getDescription())
                .status(complaint.getStatus())
                .response(complaint.getResponse())
                .submittedAt(complaint.getSubmittedAt())
                .build();
    }

    public ComplaintStatisticsResponse getStatistics() {

        User currentUser = authenticationService.getUser();

        long total = complaintsRepository.countByUser(currentUser);
        long pending = complaintsRepository.countByUserAndStatus(currentUser, "PENDING");
        long replied = complaintsRepository.countByUserAndStatus(currentUser, "REPLIED");

        return new ComplaintStatisticsResponse(
                total,
                pending,
                replied
        );
    }
}
