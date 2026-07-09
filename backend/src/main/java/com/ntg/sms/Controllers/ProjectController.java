package com.ntg.sms.Controllers;

import com.ntg.sms.Entities.Dtos.Request.ProjectRequest;
import com.ntg.sms.Entities.Dtos.Response.ProjectResponse;
import com.ntg.sms.Service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping("/{id}")
    public ProjectResponse getProjectById(@PathVariable Long id){
        return projectService.getProjectById(id);
    }

    @GetMapping
    public List<ProjectResponse> getAllProjects(){
        return projectService.getAllProjects();
    }

    @GetMapping("/course/{courseId}")
    public List<ProjectResponse> getProjectsByCourse(
            @PathVariable Long courseId){

        return projectService.getProjectsByCourse(courseId);
    }

}
