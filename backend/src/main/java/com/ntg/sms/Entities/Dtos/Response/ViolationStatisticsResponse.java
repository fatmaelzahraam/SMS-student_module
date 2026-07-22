package com.ntg.sms.Entities.Dtos.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ViolationStatisticsResponse {


    private long total;
    private long thisMonth;
    private long guardianSummons;
    private long meetings;

    public ViolationStatisticsResponse(long total, long thisMonth, long guardianSummons) {
        this.total = total;
        this.thisMonth = thisMonth;
        this.guardianSummons = guardianSummons;
        this.meetings = guardianSummons;
    }
}
