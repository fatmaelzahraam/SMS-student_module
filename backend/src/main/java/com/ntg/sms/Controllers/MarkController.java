package com.ntg.sms.Controllers;

import com.ntg.sms.Entities.Dtos.Response.MarkResponse;
import com.ntg.sms.Entities.Dtos.Response.StudentMarksDashboardResponse;
import com.ntg.sms.Service.MarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/marks")
@RequiredArgsConstructor
public class MarkController{

    private final MarkService markService;


     // Dashboard

    @GetMapping("/student/{studentId}/dashboard")
    @PreAuthorize("hasRole('STUDENT') or hasRole('ADMIN')")
    public ResponseEntity<StudentMarksDashboardResponse> getDashboard(
            @PathVariable Long studentId,
            @RequestParam(required = false) String typeName) {
        return ResponseEntity.ok(markService.getDashboard(studentId, typeName));
    }
     // All marks
    @GetMapping("/student/{studentId}")
    @PreAuthorize("hasRole('STUDENT') or hasRole('ADMIN')")
    public ResponseEntity<List<MarkResponse>> getMarksByStudent(
            @PathVariable Long studentId
    ) {

        return ResponseEntity.ok(
                markService.getMarksByStudent(studentId)
        );

    }

     // Filter by subject

    @GetMapping("/student/{studentId}/course/{courseId}")
    @PreAuthorize("hasRole('STUDENT') or hasRole('ADMIN')")
    public ResponseEntity<List<MarkResponse>> getMarksByStudentAndCourse(
            @PathVariable Long studentId,
            @PathVariable Long courseId
    ) {

        return ResponseEntity.ok(
                markService.getMarksByStudentAndCourse(studentId, courseId)
        );

    }
}