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

    public AssignmentResponse createAssignment(AssignmentRequest request){

        Set<Course> courses = new HashSet<>();

        if(request.getCourseIds() != null){
            courses.addAll(courseRepository.findAllById(request.getCourseIds()));
        }

        Assignment assignment = Assignment.builder()
                .name(request.getName())
                .deadline(request.getDeadline())
                .assignDate(request.getAssignDate())
                .description(request.getDescription())
                .fileLink(request.getFileLink())
                .studentSubmission(request.getStudentSubmission())
                .courses(courses)
                .build();

        return assignmentMapper.toResponse(
                assignmentRepository.save(assignment)
        );
    }

    public AssignmentResponse updateAssignment(Long id, AssignmentRequest request){

        Assignment assignment = assignmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Assignment not found"));

        Set<Course> courses = new HashSet<>();

        if(request.getCourseIds() != null){
            courses.addAll(courseRepository.findAllById(request.getCourseIds()));
        }

        assignment.setName(request.getName());
        assignment.setDeadline(request.getDeadline());
        assignment.setAssignDate(request.getAssignDate());
        assignment.setDescription(request.getDescription());
        assignment.setFileLink(request.getFileLink());
        assignment.setStudentSubmission(request.getStudentSubmission());
        assignment.setCourses(courses);

        return assignmentMapper.toResponse(
                assignmentRepository.save(assignment)
        );
    }

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