package com.ntg.sms.Entities.Dtos.Request;

import lombok.Data;

import java.util.Set;

@Data
public class TeamRequest {

    private Long id;
    private Long projectId;
    private String name;
    private Set<Long> studentIds;

}
