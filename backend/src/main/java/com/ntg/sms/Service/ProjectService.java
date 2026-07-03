package com.ntg.sms.Service;

import com.ntg.sms.Entities.Course;
import com.ntg.sms.Entities.Dtos.Request.ProjectRequest;
import com.ntg.sms.Entities.Dtos.Response.ProjectResponse;
import com.ntg.sms.Entities.Project;
import com.ntg.sms.Mapper.ProjectMapper;
import com.ntg.sms.Repositories.CourseRepository;
import com.ntg.sms.Repositories.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final CourseRepository courseRepository;
    private final ProjectMapper projectMapper;

    public ProjectResponse createProject(ProjectRequest request){

        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found"));

        Project project = new Project();

        project.setId(request.getId());
        project.setCourse(course);
        project.setName(request.getName());
        project.setDescription(request.getDescription());
        project.setAssignDate(request.getAssignDate());
        project.setDeadline(request.getDeadline());

        return projectMapper.toResponse(projectRepository.save(project));
    }

    public ProjectResponse updateProject(Long id, ProjectRequest request){

        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found"));

        project.setCourse(course);
        project.setName(request.getName());
        project.setDescription(request.getDescription());
        project.setAssignDate(request.getAssignDate());
        project.setDeadline(request.getDeadline());

        return projectMapper.toResponse(projectRepository.save(project));
    }

    public ProjectResponse getProjectById(Long id){

        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        return projectMapper.toResponse(project);
    }

    public List<ProjectResponse> getAllProjects(){

        return projectRepository.findAll()
                .stream()
                .map(projectMapper::toResponse)
                .toList();
    }

    public List<ProjectResponse> getProjectsByCourse(Long courseId){

        return projectRepository.findByCourseId(courseId)
                .stream()
                .map(projectMapper::toResponse)
                .toList();
    }

    public void deleteProject(Long id){

        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        projectRepository.delete(project);
    }

}
