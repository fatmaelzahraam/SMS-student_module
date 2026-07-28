package com.ntg.sms.Entities.Dtos.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentMarksDashboardResponse {
    private Double averagePercentage;
    private Double highestMark;
    private Double lowestMark;
    private Integer totalSubjects;
    private Integer academicRank;
    private List<MonthlyMarksResponse> monthlyMarks;
    private List<SubjectAverageResponse> subjectAverages;
    private List<String> markTypes;
}
