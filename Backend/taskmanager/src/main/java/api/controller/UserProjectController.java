package api.controller;

import api.dto.AssignUserToProjectRequest;
import api.model.UserProject;
import api.service.UserProjectService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user-project")

public class UserProjectController {
    @Autowired
    private UserProjectService userProjectService;

    @PostMapping
    public UserProject assignUserToProject(@RequestBody AssignUserToProjectRequest request) {

        return userProjectService.assignUserToProject(request);
    }
}
