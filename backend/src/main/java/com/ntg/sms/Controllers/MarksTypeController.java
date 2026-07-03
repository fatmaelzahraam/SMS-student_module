package com.ntg.sms.Controllers;

import com.ntg.sms.Entities.Dtos.Request.MarksTypeRequest;
import com.ntg.sms.Entities.Dtos.Response.MarksTypeResponse;
import com.ntg.sms.Service.MarksTypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/marks-types")
@RequiredArgsConstructor
public class MarksTypeController {

    private final MarksTypeService marksTypeService;

    // ─── GET ALL ─────────────────────────────────────────────────────────────

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER', 'STUDENT')")
    public ResponseEntity<List<MarksTypeResponse>> getAllTypes() {
        return ResponseEntity.ok(marksTypeService.getAllTypes());
    }

    // ─── GET BY ID ────────────────────────────────────────────────────────────

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER', 'STUDENT')")
    public ResponseEntity<MarksTypeResponse> getTypeById(@PathVariable Long id) {
        return ResponseEntity.ok(marksTypeService.getTypeById(id));
    }

    // ─── CREATE ───────────────────────────────────────────────────────────────

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MarksTypeResponse> createType(@Valid @RequestBody MarksTypeRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(marksTypeService.createType(request));
    }

    // ─── UPDATE ───────────────────────────────────────────────────────────────

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MarksTypeResponse> updateType(
            @PathVariable Long id,
            @Valid @RequestBody MarksTypeRequest request) {
        return ResponseEntity.ok(marksTypeService.updateType(id, request));
    }

    // ─── DELETE ───────────────────────────────────────────────────────────────

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteType(@PathVariable Long id) {
        marksTypeService.deleteType(id);
        return ResponseEntity.noContent().build();
    }
}
