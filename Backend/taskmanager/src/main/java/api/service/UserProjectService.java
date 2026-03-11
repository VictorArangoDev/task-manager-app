package api.service;

import api.dto.AssignUserToProjectRequest;
import api.model.Project;
import api.model.User;
import api.model.UserProject;
import api.repository.ProjectRepository;
import api.repository.UserProjectRepository;
import api.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserProjectService {

    @Autowired
    private UserProjectRepository userProjectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    public UserProject assignUserToProject(AssignUserToProjectRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));

        UserProject userProject = new UserProject();
        userProject.setUser(user);
        userProject.setProject(project);

        return userProjectRepository.save(userProject);
    }
}