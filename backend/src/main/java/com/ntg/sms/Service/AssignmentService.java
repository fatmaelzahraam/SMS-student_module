package com.ntg.sms.Service;

import com.ntg.sms.Entities.Assignment;
import com.ntg.sms.Entities.Course;
import com.ntg.sms.Entities.Dtos.Request.AssignmentRequest;
import com.ntg.sms.Entities.Dtos.Response.AssignmentResponse;
import com.ntg.sms.Entities.Dtos.Response.MarkResponse;
import com.ntg.sms.Exceptions.ResourceNotFoundException;
import com.ntg.sms.Mapper.AssignmentMapper;
import com.ntg.sms.Repositories.AssignmentRepository;
import com.ntg.sms.Repositories.CourseRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final CourseRepository courseRepository;
    private final AssignmentMapper assignmentMapper;
    public AssignmentResponse getAssignmentById(Long id){

        Assignment assignment = assignmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Assignment not found"));

        return assignmentMapper.toResponse(assignment);
    }

    public List<AssignmentResponse> getAllAssignments(){

        return assignmentRepository.findAll()
                .stream()
                .map(assignmentMapper::toResponse)
                .toList();
    }

    public List<AssignmentResponse> getAssignmentsByCourse(Long courseId){

        return assignmentRepository.findByCoursesId(courseId)
                .stream()
                .map(assignmentMapper::toResponse)
                .toList();
    }
}