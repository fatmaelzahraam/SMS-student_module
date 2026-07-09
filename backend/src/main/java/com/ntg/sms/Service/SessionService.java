package com.ntg.sms.Service;


import com.ntg.sms.Entities.Class;
import com.ntg.sms.Entities.Dtos.Response.SessionResponse;
import com.ntg.sms.Entities.Grade;
import com.ntg.sms.Repositories.ClassRepository;
import com.ntg.sms.Repositories.GradeRepository;
import com.ntg.sms.Repositories.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class SessionService {

    @Autowired
    private SessionRepository repo;

    @Autowired
    private GradeRepository gradeRepo;
    @Autowired
    private ClassRepository classRepo;

    // ========================= GET ALL SESSIONS =========================

    public List<SessionResponse> allSessions(@Param("classId") Long classId) {

        List<SessionResponse> sessions = repo.findAllSessionsByClassId(classId);

        if (sessions == null || sessions.isEmpty()) {
            return Collections.emptyList();
        }

        return sessions;
    }

    // ========================= STUDENT SCHEDULE =========================

    public List<SessionResponse> getClassSessionsByStudent(Long studentId) {
        return repo.findClassSessionsByStudentId(studentId);
    }

    public List<SessionResponse> getMonthExamsByStudent(Long studentId) {
        return repo.findMonthExamsByStudentId(studentId);
    }

    public List<SessionResponse> getFinalExamsByStudent(Long studentId) {
        return repo.findFinalExamsByStudentId(studentId);
    }




//    ===============================grade====================


    public List<Grade> getAllGrades (){
        return gradeRepo.findAll();
    }


//    =====================class====================


    public List<Class> getAllClasses (){
        return classRepo.findAll();
    }


}