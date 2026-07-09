package com.ntg.sms.Controllers;

import com.ntg.sms.Entities.Dtos.Request.TeamRequest;
import com.ntg.sms.Entities.Dtos.Response.TeamResponse;
import com.ntg.sms.Service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/projects/teams")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    @GetMapping("/{id}")
    public TeamResponse getTeamById(@PathVariable Long id) {
        return teamService.getTeamById(id);
    }

    @GetMapping
    public List<TeamResponse> getAllTeams() {
        return teamService.getAllTeams();
    }

    @GetMapping("/project/{projectId}")
    public List<TeamResponse> getTeamsByProject(@PathVariable Long projectId) {
        return teamService.getTeamsByProject(projectId);
    }

}
