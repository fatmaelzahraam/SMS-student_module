package com.ntg.sms.Entities.Dtos.Response;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class TeamResponse {

    private Long id;
    private Long projectId;
    private String projectName;
    private String name;
    private Set<Long> studentIds;

}
