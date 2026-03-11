package api.service;

import api.dto.*;
import api.exception.ResourceNotFoundException;
import api.model.Project;
import api.model.Role;
import api.model.StateProjectTask;
import api.repository.ProjectRepository;
import api.repository.StateProjectTaskRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private StateProjectTaskRepository stateProjectTaskRepository;

    public Project createProject(CreateProjectRequest request) {

        StateProjectTask state = stateProjectTaskRepository
                .findById(request.getStateProjectTaskId())
                .orElseThrow(() -> new ResourceNotFoundException("Estado de proyecto no encontrado"));

        Project project = new Project();

        project.setName(request.getName());
        project.setDescription(request.getDescription());
        project.setStartDate(request.getStartDate());
        project.setEndDate(request.getEndDate());
        project.setDeadline(request.getDeadline());
        project.setStateProjectTask(state);

        return projectRepository.save(project);
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Project getProjectById(Long id) {

        return projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));
    }

    public Project updateProject(Long id, UpdateProjectRequest request) {

        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));

        StateProjectTask state = stateProjectTaskRepository
                .findById(request.getStateProjectTaskId())
                .orElseThrow(() -> new RuntimeException("Estado no encontrado"));

        project.setName(request.getName());
        project.setDescription(request.getDescription());
        project.setStartDate(request.getStartDate());
        project.setEndDate(request.getEndDate());
        project.setDeadline(request.getDeadline());
        project.setStateProjectTask(state);

        return projectRepository.save(project);
    }
    
}

