package com.ntg.sms.Service;

import com.ntg.sms.Entities.Course;
import com.ntg.sms.Entities.Dtos.Response.CourseResponse;
import com.ntg.sms.Mapper.CourseMapper;
import com.ntg.sms.Repositories.CourseRepository;
import com.ntg.sms.Repositories.TeacherRepository;
import com.ntg.sms.Repositories.TermRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;
    private final TermRepository termRepository;
    private final CourseMapper courseMapper;

    @Transactional(readOnly = true)
    public List<CourseResponse> getAllCourses() {
        return courseRepository.findAllWithDetails()
                .stream()
                .map(courseMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CourseResponse getCourseById(Long id) {
        Course course = courseRepository.findByIdWithAssignments(id)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + id));
        return courseMapper.toResponse(course);
    }

    @Transactional(readOnly = true)
    public List<CourseResponse> getCoursesByTeacher(Long teacherId) {
        if (!teacherRepository.existsById(teacherId))
            throw new EntityNotFoundException("Teacher not found with id: " + teacherId);
        return courseRepository.findByTeacherId(teacherId)
                .stream()
                .map(courseMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CourseResponse> getCoursesByTerm(Long termId) {
        if (!termRepository.existsById(termId))
            throw new EntityNotFoundException("Term not found with id: " + termId);
        return courseRepository.findByTermId(termId)
                .stream()
                .map(courseMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CourseResponse> getCoursesByType(String courseType) {
        return courseRepository.findByCourseType(courseType)
                .stream()
                .map(courseMapper::toResponse)
                .collect(Collectors.toList());
    }
}