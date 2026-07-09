package com.ntg.sms.Entities.Dtos.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ComplaintStatisticsResponse {

    private long total;

    private long pending;

    private long replied;
}