package api.service;

import api.dto.CreateTaskRequest;
import api.dto.UpdateTaskRequest;
import api.exception.ResourceNotFoundException;
import api.model.Priority;
import api.model.Project;
import api.model.StateProjectTask;
import api.model.Task;
import api.repository.PriorityRepository;
import api.repository.ProjectRepository;
import api.repository.StateProjectTaskRepository;
import api.repository.TaskRepository;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final PriorityRepository priorityRepository;
    private final StateProjectTaskRepository stateRepository;
    private final ProjectRepository projectRepository;

    public TaskService(
            TaskRepository taskRepository,
            PriorityRepository priorityRepository,
            StateProjectTaskRepository stateRepository,
            ProjectRepository projectRepository) {
        this.taskRepository = taskRepository;
        this.priorityRepository = priorityRepository;
        this.stateRepository = stateRepository;
        this.projectRepository = projectRepository;
    }

    // Retorna todas las tareas
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    // Busca una tarea por id — lanza excepción si no existe
    public Task findById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarea no encontrada"));
    }

    // create — recibe CreateTaskRequest en vez de Task
    public Task create(CreateTaskRequest request) {

        if (request.getStateProjectTaskId() == null) {
            throw new IllegalArgumentException("El estado es obligatorio");
        }
        StateProjectTask state = stateRepository.findById(request.getStateProjectTaskId())
                .orElseThrow(() -> new ResourceNotFoundException("Estado no encontrado"));

        if (request.getPriorityId() == null) {
            throw new IllegalArgumentException("La prioridad es obligatoria");
        }
        Priority priority = priorityRepository.findById(request.getPriorityId())
                .orElseThrow(() -> new ResourceNotFoundException("Prioridad no encontrada"));

        if (request.getProjectId() == null) {
            throw new IllegalArgumentException("El proyecto es obligatorio");
        }
        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Proyecto no encontrado"));

        // Construye el Task desde el DTO
        Task task = new Task();
        task.setName(request.getName());
        task.setDescription(request.getDescription());
        task.setStartDate(request.getStartDate());
        task.setEndDate(request.getEndDate());
        task.setDeadline(request.getDeadline());
        task.setStateProjectTask(state);
        task.setPriority(priority);
        task.setProject(project);

        return taskRepository.save(task);
    }

    // update — recibe UpdateTaskRequest en vez de Task
    public Task update(Long id, UpdateTaskRequest request) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarea no encontrada"));

        if (request.getName() != null && !request.getName().trim().isEmpty()) {
            task.setName(request.getName());
        }
        if (request.getDescription() != null) {
            task.setDescription(request.getDescription());
        }
        if (request.getStartDate() != null) {
            task.setStartDate(request.getStartDate());
        }
        if (request.getEndDate() != null) {
            task.setEndDate(request.getEndDate());
        }
        if (request.getDeadline() != null) {
            task.setDeadline(request.getDeadline());
        }
        if (request.getPriorityId() != null) {
            Priority priority = priorityRepository.findById(request.getPriorityId())
                    .orElseThrow(() -> new ResourceNotFoundException("Prioridad no encontrada"));
            task.setPriority(priority);
        }

        return taskRepository.save(task);
    }

    // Cambia el estado de una tarea — TODO → IN_PROGRESS → DONE → CANCELLED
    public Task updateStatus(Long id, Long stateId) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarea no encontrada"));

        StateProjectTask state = stateRepository.findById(stateId)
                .orElseThrow(() -> new ResourceNotFoundException("Estado no encontrado"));

        task.setStateProjectTask(state);
        return taskRepository.save(task);
    }
}