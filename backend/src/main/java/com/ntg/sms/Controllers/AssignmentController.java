package com.ntg.sms.Controllers;

import com.ntg.sms.Entities.Dtos.Response.AssignmentResponse;
import com.ntg.sms.Service.AssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/assignments")
@RequiredArgsConstructor
public class AssignmentController {

    private final AssignmentService assignmentService;


    @GetMapping("/{studentid}")
    public AssignmentResponse getAssignmentById(@PathVariable Long id){

        return assignmentService.getAssignmentById(id);
    }

    @GetMapping
    public List<AssignmentResponse> getAllAssignments(){
        return assignmentService.getAllAssignments();
    }

    @GetMapping("/course/{courseId}")
    public List<AssignmentResponse> getAssignmentsByCourse(
            @PathVariable Long courseId){

        return assignmentService.getAssignmentsByCourse(courseId);
    }

}