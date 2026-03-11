package api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nombre de la tarea — obligatorio
    @NotBlank(message = "El nombre es obligatorio")
    @Column(nullable = false)
    private String name;

    // Descripción detallada — opcional
    @Column(columnDefinition = "TEXT")
    private String description;

    // Fecha de inicio — opcional
    @Column(name = "start_date")
    private LocalDateTime startDate;

    // Fecha de fin — opcional
    @Column(name = "end_date")
    private LocalDateTime endDate;

    // Fecha límite — opcional
    @Column(name = "deadline")
    private LocalDateTime deadline;

    // Relación con el estado (TODO, IN_PROGRESS, DONE, CANCELLED)
    @NotNull(message = "El estado es obligatorio")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_state_project_task", nullable = false)
    private StateProjectTask stateProjectTask;

    // Relación con la prioridad (HIGH, MEDIUM, LOW)
    @NotNull(message = "La prioridad es obligatoria")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_priority", nullable = false)
    private Priority priority;

    // Relación con el proyecto al que pertenece
    @NotNull(message = "El proyecto es obligatorio")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_project", nullable = false)
    private Project project;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "update_at")
    private LocalDateTime updatedAt;

    public Task() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getStartDate() { return startDate; }
    public void setStartDate(LocalDateTime startDate) { this.startDate = startDate; }

    public LocalDateTime getEndDate() { return endDate; }
    public void setEndDate(LocalDateTime endDate) { this.endDate = endDate; }

    public LocalDateTime getDeadline() { return deadline; }
    public void setDeadline(LocalDateTime deadline) { this.deadline = deadline; }

    public StateProjectTask getStateProjectTask() { return stateProjectTask; }
    public void setStateProjectTask(StateProjectTask s) { this.stateProjectTask = s; }

    public Priority getPriority() { return priority; }
    public void setPriority(Priority priority) { this.priority = priority; }

    public Project getProject() { return project; }
    public void setProject(Project project) { this.project = project; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}