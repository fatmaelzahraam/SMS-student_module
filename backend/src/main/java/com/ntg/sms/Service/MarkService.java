package com.ntg.sms.Service;

import com.ntg.sms.Entities.Dtos.Response.*;
import com.ntg.sms.Entities.Mark;
import com.ntg.sms.Mapper.MarkMapper;
import com.ntg.sms.Repositories.MarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MarkService {

    private final MarkRepository markRepository;
    private final MarkMapper markMapper;

    @Transactional(readOnly = true)
    public List<MarkResponse> getMarksByStudent(Long studentId) {

        return markRepository.findByStudentId(studentId)
                .stream()
                .map(markMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<MarkResponse> getMarksByStudentAndCourse(Long studentId,
                                                         Long courseId) {

        return markRepository.findByStudentIdAndCourseId(studentId, courseId)
                .stream()
                .map(markMapper::toResponse)
                .toList();
    }


     // Dashboard API

    @Transactional(readOnly = true)
    public StudentMarksDashboardResponse getDashboard(Long studentId) {

        Double average = Optional.ofNullable(
                        markRepository.calculatePerformance(studentId))
                .orElse(0.0);

        Double highest = Optional.ofNullable(
                        markRepository.highestPercentage(studentId))
                .orElse(0.0);

        Double lowest = Optional.ofNullable(
                        markRepository.lowestPercentage(studentId))
                .orElse(0.0);

        Integer totalSubjects = Optional.ofNullable(
                        markRepository.totalSubjects(studentId))
                .orElse(0);

        Integer rank = Optional.ofNullable(
                        markRepository.getStudentRank(studentId))
                .orElse(0);

        List<SubjectAverageResponse> chart =
                markRepository.getSubjectAverages(studentId);

        List<Mark> marks = markRepository.findByStudentId(studentId);

        List<MonthlyMarksResponse> monthlyMarks =
                buildMonthlyTable(marks);

        return StudentMarksDashboardResponse.builder()
                .averagePercentage(round(average))
                .highestMark(round(highest))
                .lowestMark(round(lowest))
                .totalSubjects(totalSubjects)
                .academicRank(rank)
                .subjectAverages(chart)
                .monthlyMarks(monthlyMarks)
                .build();
    }

    //Creates the Monthly Exam Marks table.

    private List<MonthlyMarksResponse> buildMonthlyTable(List<Mark> marks) {

        Map<String, List<Mark>> groupedByMonth =
                marks.stream()
                        .collect(Collectors.groupingBy(
                                mark -> mark.getType().getTypeName(),
                                LinkedHashMap::new,
                                Collectors.toList()));

        List<MonthlyMarksResponse> response = new ArrayList<>();

        for (Map.Entry<String, List<Mark>> entry : groupedByMonth.entrySet()) {

            List<SubjectMarkResponse> subjects = new ArrayList<>();

            for (Mark mark : entry.getValue()) {

                double percentage =
                        (mark.getScore() * 100.0) / mark.getMaxScore();

                subjects.add(
                        SubjectMarkResponse.builder()
                                .subject(mark.getCourse().getCourseName())
                                .percentage(round(percentage))
                                .build()
                );
            }

            response.add(
                    MonthlyMarksResponse.builder()
                            .month(entry.getKey())
                            .subjects(subjects)
                            .build()
            );
        }

        return response;
    }

    //Round to 2 decimal places.

    private Double round(Double value) {

        return Math.round(value * 100.0) / 100.0;

    }

}