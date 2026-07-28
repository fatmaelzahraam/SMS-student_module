package com.ntg.sms.Service;

import com.ntg.sms.Entities.Dtos.Response.*;
import com.ntg.sms.Entities.Mark;
import com.ntg.sms.Mapper.MarkMapper;
import com.ntg.sms.Repositories.MarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.TextStyle;
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
    public List<MarkResponse> getMarksByStudentAndCourse(Long studentId, Long courseId) {
        return markRepository.findByStudentIdAndCourseId(studentId, courseId)
                .stream()
                .map(markMapper::toResponse)
                .toList();
    }

    public int getPerformanceScore(Long studentId) {
        List<Mark> marks = markRepository.findByStudentId(studentId);
        if (marks.isEmpty()) return 0;
        double totalMaxScore = marks.stream().mapToDouble(m -> m.getMaxScore().doubleValue()).sum();
        double totalStudentScore = marks.stream().mapToDouble(m -> m.getScore().doubleValue()).sum();
        if (totalMaxScore == 0) return 0;
        return (int) ((totalStudentScore / totalMaxScore) * 100);
    }

    // ── Dashboard ─────────────────────────────────────────────────────────────

    @Transactional(readOnly = true)
    public StudentMarksDashboardResponse getDashboard(Long studentId, String typeName) {

        Double average = Optional.ofNullable(markRepository.calculatePerformance(studentId)).orElse(0.0);
        Double highest = Optional.ofNullable(markRepository.highestPercentage(studentId)).orElse(0.0);
        Double lowest  = Optional.ofNullable(markRepository.lowestPercentage(studentId)).orElse(0.0);
        Integer totalSubjects = Optional.ofNullable(markRepository.totalSubjects(studentId)).orElse(0);
        Integer rank = Optional.ofNullable(markRepository.getStudentRank(studentId)).orElse(0);

        List<SubjectAverageResponse> chart = markRepository.getSubjectAverages(studentId);

        // All marks — used for type list and monthly table
        List<Mark> allMarks = markRepository.findByStudentId(studentId);

        // Distinct type names for the frontend dropdown
        List<String> markTypes = allMarks.stream()
                .map(m -> m.getType().getTypeName())
                .distinct()
                .sorted()
                .toList();

        // Filter by type if provided
        List<Mark> filtered = (typeName != null && !typeName.isBlank())
                ? allMarks.stream()
                .filter(m -> m.getType().getTypeName().equalsIgnoreCase(typeName))
                .toList()
                : allMarks;

        List<MonthlyMarksResponse> monthlyMarks = buildMonthlyTable(filtered);

        return StudentMarksDashboardResponse.builder()
                .averagePercentage(round(average))
                .highestMark(round(highest))
                .lowestMark(round(lowest))
                .totalSubjects(totalSubjects)
                .academicRank(rank)
                .subjectAverages(chart)
                .monthlyMarks(monthlyMarks)
                .markTypes(markTypes)
                .build();
    }

    // ── Monthly table: rows = months, columns = subjects ──────────────────────

    private List<MonthlyMarksResponse> buildMonthlyTable(List<Mark> marks) {

        // Group by "Month Year" e.g. "September 2024", preserve insertion order
        Map<String, List<Mark>> groupedByMonth = marks.stream()
                .collect(Collectors.groupingBy(
                        m -> m.getFeedbackDate()
                                .getMonth()
                                .getDisplayName(TextStyle.FULL, Locale.ENGLISH)
                                + " " + m.getFeedbackDate().getYear(),
                        LinkedHashMap::new,
                        Collectors.toList()
                ));

        List<MonthlyMarksResponse> response = new ArrayList<>();

        for (Map.Entry<String, List<Mark>> entry : groupedByMonth.entrySet()) {

            // Average percentage per subject within this month
            Map<String, Double> subjectPercentages = entry.getValue().stream()
                    .collect(Collectors.groupingBy(
                            m -> m.getCourse().getCourseName(),
                            Collectors.averagingDouble(
                                    m -> (m.getScore() * 100.0) / m.getMaxScore()
                            )
                    ));

            List<SubjectMarkResponse> subjects = subjectPercentages.entrySet().stream()
                    .map(e -> SubjectMarkResponse.builder()
                            .subject(e.getKey())
                            .percentage(round(e.getValue()))
                            .build())
                    .toList();

            response.add(MonthlyMarksResponse.builder()
                    .month(entry.getKey())
                    .subjects(subjects)
                    .build());
        }

        return response;
    }

    private Double round(Double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}