package api.controller;

import api.dto.ApiResponse;
import api.dto.CreateTaskRequest;
import api.dto.UpdateTaskRequest;
import api.model.Task;
import api.service.TaskService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // GET /api/tasks — retorna todas las tareas
    @GetMapping
    public ResponseEntity<ApiResponse> findAll() {
        List<Task> tasks = taskService.findAll();
        if (tasks.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(new ApiResponse("success", 204, "No hay tareas registradas", tasks));
        }
        return ResponseEntity.ok(
                new ApiResponse("success", 200, "Lista de tareas obtenida correctamente", tasks));
    }

    // GET /api/tasks/{id} — retorna una tarea por id
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> findById(@PathVariable Long id) {
        Task task = taskService.findById(id);
        return ResponseEntity.ok(
                new ApiResponse("success", 200, "Tarea encontrada", task));
    }

    // POST /api/tasks — crea una nueva tarea
    @PostMapping
    public ResponseEntity<ApiResponse> create(@Valid @RequestBody CreateTaskRequest request) {
        Task saved = taskService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse("success", 201, "Tarea creada correctamente", saved));
    }

    // PUT /api/tasks/{id} — actualiza datos de una tarea
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> update(
            @PathVariable Long id,
            @RequestBody UpdateTaskRequest request) {
        Task updated = taskService.update(id, request);
        return ResponseEntity.ok(
                new ApiResponse("success", 200, "Tarea actualizada correctamente", updated));
    }

    // PUT /api/tasks/{id}/status — cambia el estado de una tarea
    @PutMapping("/{id}/status")
    public ResponseEntity<ApiResponse> updateStatus(
            @PathVariable Long id,
            @RequestBody Map<String, Long> body) {
        Long stateId = body.get("stateId");
        if (stateId == null) {
            throw new IllegalArgumentException("El stateId es obligatorio");
        }
        Task updated = taskService.updateStatus(id, stateId);
        return ResponseEntity.ok(
                new ApiResponse("success", 200, "Estado actualizado correctamente", updated));
    }
}