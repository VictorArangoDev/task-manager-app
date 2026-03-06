package api.controller;

import org.springframework.web.bind.annotation.*;
import api.model.Priority;
import api.service.PriorityService;

import java.util.List;  
import java.util.Optional;

@RestController
@RequestMapping("/api/priorities")
public class PriorityController {
    private final PriorityService priorityService;

    public PriorityController(PriorityService priorityService) {
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

    @PostMapping
    public Priority createPriority(@RequestBody Priority priority) {
        return priorityService.createPriority(priority);
    }

    @PutMapping("/{id}")
    public Priority updatePriority(@PathVariable Long id, @RequestBody Priority priorityDetails) {
        return priorityService.updatePriority(id, priorityDetails);
    }

}
