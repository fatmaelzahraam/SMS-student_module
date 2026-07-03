package com.ntg.sms.Controllers;

import com.ntg.sms.Entities.Dtos.Request.SessionRequest;
import com.ntg.sms.Entities.Dtos.Response.SessionResponse;
import com.ntg.sms.Service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sessions")
@RequiredArgsConstructor
public class SessionController {

    private final SessionService sessionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SessionResponse createSession(@RequestBody SessionRequest request) {
        return sessionService.createSession(request);
    }

    @PutMapping("/{id}")
    public SessionResponse updateSession(
            @PathVariable Long id,
            @RequestBody SessionRequest request) {

        return sessionService.updateSession(id, request);
    }

    @GetMapping("/{id}")
    public SessionResponse getSessionById(@PathVariable Long id) {
        return sessionService.getSessionById(id);
    }

    @GetMapping
    public List<SessionResponse> getAllSessions() {
        return sessionService.getAllSessions();
    }

    @GetMapping("/course/{courseId}")
    public List<SessionResponse> getSessionsByCourse(@PathVariable Long courseId) {
        return sessionService.getSessionsByCourse(courseId);
    }

    @GetMapping("/class/{classId}")
    public List<SessionResponse> getSessionsByClass(@PathVariable Long classId) {
        return sessionService.getSessionsByClass(classId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSession(@PathVariable Long id) {
        sessionService.deleteSession(id);
    }

}
