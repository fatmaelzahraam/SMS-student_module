package com.ntg.sms.Service;

import com.ntg.sms.Entities.Class;
import com.ntg.sms.Entities.Course;
import com.ntg.sms.Entities.Dtos.Request.SessionRequest;
import com.ntg.sms.Entities.Dtos.Response.SessionResponse;
import com.ntg.sms.Entities.Session;
import com.ntg.sms.Mapper.SessionMapper;
import com.ntg.sms.Repositories.ClassRepository;
import com.ntg.sms.Repositories.CourseRepository;
import com.ntg.sms.Repositories.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;
    private final ClassRepository classRepository;
    private final CourseRepository courseRepository;
    private final SessionMapper sessionMapper;

    public SessionResponse createSession(SessionRequest request) {

        Class classField = classRepository.findById(request.getClassId())
                .orElseThrow(() -> new RuntimeException("Class not found"));

        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found"));

        Session session = new Session();

        session.setClassField(classField);
        session.setCourse(course);
        session.setDayOfWeek(request.getDayOfWeek());
        session.setStartAt(request.getStartAt());
        session.setEndAt(request.getEndAt());
        session.setUpdatedAt(request.getUpdatedAt());

        return sessionMapper.toResponse(sessionRepository.save(session));
    }

    public SessionResponse updateSession(Long id, SessionRequest request) {

        Session session = sessionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Session not found"));

        Class classField = classRepository.findById(request.getClassId())
                .orElseThrow(() -> new RuntimeException("Class not found"));

        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found"));

        session.setClassField(classField);
        session.setCourse(course);
        session.setDayOfWeek(request.getDayOfWeek());
        session.setStartAt(request.getStartAt());
        session.setEndAt(request.getEndAt());
        session.setUpdatedAt(request.getUpdatedAt());

        return sessionMapper.toResponse(sessionRepository.save(session));
    }

    public SessionResponse getSessionById(Long id) {

        Session session = sessionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Session not found"));

        return sessionMapper.toResponse(session);
    }

    public List<SessionResponse> getAllSessions() {

        return sessionRepository.findAll()
                .stream()
                .map(sessionMapper::toResponse)
                .toList();
    }

    public List<SessionResponse> getSessionsByCourse(Long courseId) {

        return sessionRepository.findByCourseId(courseId)
                .stream()
                .map(sessionMapper::toResponse)
                .toList();
    }

    public List<SessionResponse> getSessionsByClass(Long classId) {

        return sessionRepository.findByClassFieldId(classId)
                .stream()
                .map(sessionMapper::toResponse)
                .toList();
    }

    public void deleteSession(Long id) {

        Session session = sessionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Session not found"));

        sessionRepository.delete(session);
    }

}
