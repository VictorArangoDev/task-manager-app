package api.service;

import api.dto.CreateStateProjectTaskRequest;
import api.dto.UpdateStateProjectTaskRequest;
import api.model.StateProjectTask;
import api.repository.StateProjectTaskRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StateProjectTaskService {

    @Autowired
    private StateProjectTaskRepository repository;

    // Crear estado
    public StateProjectTask createState(CreateStateProjectTaskRequest request) {

        StateProjectTask state = new StateProjectTask();
        state.setName(request.getName());

        return repository.save(state);
    }

    // Listar estados
    public List<StateProjectTask> getAllStates() {
        return repository.findAll();
    }

    // Obtener estado por ID
    public StateProjectTask getStateById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estado no encontrado"));
    }

    // Editar estado
    public StateProjectTask updateState(Long id, UpdateStateProjectTaskRequest request) {

        StateProjectTask state = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estado no encontrado"));

        state.setName(request.getName());

        return repository.save(state);
    }
}