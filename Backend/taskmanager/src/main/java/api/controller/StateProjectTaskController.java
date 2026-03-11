package api.controller;

import api.dto.CreateStateProjectTaskRequest;
import api.dto.UpdateStateProjectTaskRequest;
import api.model.StateProjectTask;
import api.service.StateProjectTaskService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/state-project-task")
public class StateProjectTaskController {

    @Autowired
    private StateProjectTaskService service;

    // Crear estado
    @PostMapping
    public StateProjectTask createState(@RequestBody CreateStateProjectTaskRequest request) {
        return service.createState(request);
    }

    // Listar estados
    @GetMapping
    public List<StateProjectTask> getAllStates() {
        return service.getAllStates();
    }

    // Obtener estado por ID
    @GetMapping("/{id}")
    public StateProjectTask getStateById(@PathVariable Long id) {
        return service.getStateById(id);
    }

    // Editar estado
    @PutMapping("/{id}")
    public StateProjectTask updateState(
            @PathVariable Long id,
            @RequestBody UpdateStateProjectTaskRequest request) {

        return service.updateState(id, request);
    }
}
