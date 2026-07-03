package com.ntg.sms.Service;



import com.ntg.sms.Entities.Course;
import com.ntg.sms.Entities.Mark;
import com.ntg.sms.Entities.Dtos.Response.MarkResponse;
import com.ntg.sms.Entities.Dtos.Request.MarkRequest;
import com.ntg.sms.Entities.MarksType;
import com.ntg.sms.Entities.User;
import com.ntg.sms.Exceptions.ResourceNotFoundException;
import com.ntg.sms.Mapper.MarkMapper;
import com.ntg.sms.Repositories.CourseRepository;
import com.ntg.sms.Repositories.MarkRepository;
import com.ntg.sms.Repositories.MarksTypeRepository;
import com.ntg.sms.Repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MarkService {

    private final MarkRepository markRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final MarksTypeRepository marksTypeRepository;
    private final MarkMapper markMapper;

    // ─── READ ─────────────────────────────────────────────────────────────────

    @Transactional(readOnly = true)
    public List<MarkResponse> getAllMarks() {
        return markRepository.findAll()
                .stream()
                .map(markMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public MarkResponse getMarkById(Long id) {
        return markMapper.toResponse(findMarkOrThrow(id));
    }

    @Transactional(readOnly = true)
    public List<MarkResponse> getMarksByUser(Long userId) {
        assertUserExists(userId);
        return markRepository.findByUserId(userId)
                .stream()
                .map(markMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<MarkResponse> getMarksByCourse(Long courseId) {
        assertCourseExists(courseId);
        return markRepository.findByCourseId(courseId)
                .stream()
                .map(markMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<MarkResponse> getMarksByUserAndCourse(Long userId, Long courseId) {
        assertUserExists(userId);
        assertCourseExists(courseId);
        return markRepository.findByUserIdAndCourseId(userId, courseId)
                .stream()
                .map(markMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<MarkResponse> getApprovedMarksByUser(Long userId) {
        assertUserExists(userId);
        return markRepository.findApprovedMarksByUser(userId)
                .stream()
                .map(markMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Long getTotalApprovedScore(Long userId, Long courseId) {
        assertUserExists(userId);
        assertCourseExists(courseId);
        return markRepository.sumApprovedScoreByUserAndCourse(userId, courseId);
    }

    // ─── HELPERS ─────────────────────────────────────────────────────────────

    private Mark findMarkOrThrow(Long id) {
        return markRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Mark not found with id: " + id));
    }

    private Course findCourseOrThrow(Long courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + courseId));
    }

    private User findUserOrThrow(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
    }

    private MarksType findTypeOrThrow(Long typeId) {
        return marksTypeRepository.findById(typeId)
                .orElseThrow(() -> new EntityNotFoundException("MarksType not found with id: " + typeId));
    }

    private void assertUserExists(Long userId) {
        if (!userRepository.existsById(userId))
            throw new EntityNotFoundException("User not found with id: " + userId);
    }

    private void assertCourseExists(Long courseId) {
        if (!courseRepository.existsById(courseId))
            throw new EntityNotFoundException("Course not found with id: " + courseId);
    }

    private void validateScoreRange(Long score, Long maxScore) {
        if (score > maxScore) {
            throw new IllegalArgumentException(
                    "Score (" + score + ") cannot exceed max score (" + maxScore + ").");
        }
    }

    @Transactional(readOnly = true)
    public MarkResponse getStudentPerformance(Long studentId) {

        return markRepository.getStudentPerformance(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));}
}
