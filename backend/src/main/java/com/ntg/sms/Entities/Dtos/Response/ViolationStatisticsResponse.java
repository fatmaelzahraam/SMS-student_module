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
}
