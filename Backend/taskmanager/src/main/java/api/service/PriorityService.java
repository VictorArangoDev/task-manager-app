package api.service;

import api.model.Priority;
import api.repository.PriorityRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PriorityService {
    private final PriorityRepository priorityRepository;

    public PriorityService(PriorityRepository priorityRepository) {
        this.priorityRepository = priorityRepository;
    }

    public List<Priority> getAllPriorities() {
        return priorityRepository.findAll();
    }

    public Optional<Priority> getPriorityById(Long id) {
        return priorityRepository.findById(id);
    }

    public Priority createPriority(Priority priority) {
        return priorityRepository.save(priority);
    }

    public List<Priority> findAll() {
        return priorityRepository.findAll();
    }

    public Priority updatePriority(Long id, Priority priorityDetails) {

        Priority priority = priorityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prioridad no encontrada"));

        priority.setName(priorityDetails.getName());

        return priorityRepository.save(priority);
    }
}
