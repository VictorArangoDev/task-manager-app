package api.repository;

import api.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    // Busca tareas por proyecto
    List<Task> findByProjectId(Long projectId);

    // Busca tareas por prioridad
    List<Task> findByPriorityId(Long priorityId);

    // Busca tareas por estado
    List<Task> findByStateProjectTaskId(Long stateId);
}