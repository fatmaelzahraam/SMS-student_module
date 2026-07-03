package com.ntg.sms.Controllers;
//
//import com.ntg.sms.Entities.Assignment;
//import com.ntg.sms.Service.AssignmentService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@CrossOrigin(origins = "http://localhost:4200")
//@RequestMapping("/api/v1/assignments")
//public class AssignmentController {
//
//    @Autowired
//    private AssignmentService assignmentService;
//
//    @GetMapping
//    public List<Assignment> getAllAssignments() {
//        return assignmentService.getAllAssignments();
//    }
//}


import com.ntg.sms.Entities.Dtos.Request.AssignmentRequest;
import com.ntg.sms.Entities.Dtos.Response.AssignmentResponse;
import com.ntg.sms.Service.AssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1/assignments")
@RequiredArgsConstructor
public class AssignmentController {

    private final AssignmentService assignmentService;


    @GetMapping("/{id}")
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