package com.ntg.sms.Controllers;

import com.ntg.sms.Entities.Dtos.Request.GradeRequest;
import com.ntg.sms.Entities.Dtos.Response.GradeResponse;
import com.ntg.sms.Service.GradeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/grades")
@RequiredArgsConstructor
public class GradeController {

    private final GradeService gradeService;

    // ─── GET ALL ─────────────────────────────────────────────────────────────

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER', 'STUDENT')")
    public ResponseEntity<List<GradeResponse>> getAllGrades() {
        return ResponseEntity.ok(gradeService.getAllGrades());
    }

    // ─── GET BY ID ────────────────────────────────────────────────────────────

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER', 'STUDENT')")
    public ResponseEntity<GradeResponse> getGradeById(@PathVariable Long id) {
        return ResponseEntity.ok(gradeService.getGradeById(id));
    }

    // ─── GET BY TERM ──────────────────────────────────────────────────────────

    @GetMapping("/term/{termId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER', 'STUDENT')")
    public ResponseEntity<List<GradeResponse>> getGradesByTerm(@PathVariable Long termId) {
        return ResponseEntity.ok(gradeService.getGradesByTerm(termId));
    }
}
