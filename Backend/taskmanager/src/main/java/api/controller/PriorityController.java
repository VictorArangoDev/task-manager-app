package api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import api.dto.ApiResponse;
import api.model.Priority;
import api.service.PriorityService;
import jakarta.validation.Valid;

import java.util.List;  
import java.util.Optional;

@RestController
@RequestMapping("/api/priorities")
public class PriorityController {
    private final PriorityService priorityService;

    public PriorityController(@Valid PriorityService priorityService) {
        this.priorityService = priorityService;
    }

    @GetMapping
    public List<Priority> getAllPriorities() {
        return priorityService.getAllPriorities();
    }

    @GetMapping("/{id}")
    public Optional<Priority> getPriorityById(@PathVariable Long id) {
        return priorityService.getPriorityById(id);
    }

    // @PostMapping
    // public Priority createPriority(@RequestBody Priority priority) {
    //     return priorityService.createPriority(priority);
    // }

    @PostMapping
    public ResponseEntity<ApiResponse>  createPriority(@RequestBody Priority priority) {
        Priority saved = priorityService.createPriority(priority);
        return ResponseEntity.status(HttpStatus.CREATED).body(
            new ApiResponse("Success", 201, "prioridad creada correctamente", saved)
        );
    }

    @PutMapping("/{id}")
    public Priority updatePriority(@PathVariable Long id, @RequestBody Priority priorityDetails) {
        return priorityService.updatePriority(id, priorityDetails);
    }

}
