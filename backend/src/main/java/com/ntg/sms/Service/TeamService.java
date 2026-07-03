package com.ntg.sms.Service;

import com.ntg.sms.Entities.Project;
import com.ntg.sms.Entities.Student;
import com.ntg.sms.Entities.Team;
import com.ntg.sms.Entities.Dtos.Request.TeamRequest;
import com.ntg.sms.Entities.Dtos.Response.TeamResponse;
import com.ntg.sms.Mapper.TeamMapper;
import com.ntg.sms.Repositories.ProjectRepository;
import com.ntg.sms.Repositories.StudentRepository;
import com.ntg.sms.Repositories.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final ProjectRepository projectRepository;
    private final StudentRepository studentRepository;
    private final TeamMapper teamMapper;

    public TeamResponse createTeam(TeamRequest request) {

        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new RuntimeException("Project not found"));

        Set<Student> students = new HashSet<>();

        if (request.getStudentIds() != null) {
            students.addAll(studentRepository.findAllById(request.getStudentIds()));
        }

        Team team = new Team();

        team.setId(request.getId());
        team.setProject(project);
        team.setName(request.getName());
        team.setStudents(students);

        return teamMapper.toResponse(teamRepository.save(team));
    }

    public TeamResponse updateTeam(Long id, TeamRequest request) {

        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Team not found"));

        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new RuntimeException("Project not found"));

        Set<Student> students = new HashSet<>();

        if (request.getStudentIds() != null) {
            students.addAll(studentRepository.findAllById(request.getStudentIds()));
        }

        team.setProject(project);
        team.setName(request.getName());
        team.setStudents(students);

        return teamMapper.toResponse(teamRepository.save(team));
    }

    public TeamResponse getTeamById(Long id) {

        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Team not found"));

        return teamMapper.toResponse(team);
    }

    public List<TeamResponse> getAllTeams() {

        return teamRepository.findAll()
                .stream()
                .map(teamMapper::toResponse)
                .toList();
    }

    public List<TeamResponse> getTeamsByProject(Long projectId) {

        return teamRepository.findByProjectId(projectId)
                .stream()
                .map(teamMapper::toResponse)
                .toList();
    }

    public void deleteTeam(Long id) {

        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Team not found"));

        teamRepository.delete(team);
    }

}
