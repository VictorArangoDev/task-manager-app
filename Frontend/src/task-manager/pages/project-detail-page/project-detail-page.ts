import { Component, OnInit, computed, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { ProjectsService } from '../services/projects.service';
import { UserService } from '../services/user.service';
import { TasksService } from '../services/tasks.service';
import { ProjectResponse } from '../projects-page/interfaces/project.interface';
import { TasksResponse } from '../task-list--page/interfaces/tasks.interfaces';
import { UserInterface } from '../users-page/user.interface';
import { FormsModule } from '@angular/forms';
import { rxResource } from '@angular/core/rxjs-interop';
import { of } from 'rxjs';
import { StatusClassesPipe } from '../../../app/status-classes-pipe';
import { PriorityPipe } from '../../../app/priority-pipe';
import { InicialesPipe } from '../../../app/iniciales-pipe';

@Component({
  selector: 'project-detail-page',
  standalone: true,
  imports: [CommonModule, RouterLink, FormsModule,StatusClassesPipe, PriorityPipe, InicialesPipe],
  templateUrl: './project-detail-page.html',
})
export class ProjectDetailPage implements OnInit {

  private readonly route = inject(ActivatedRoute);
  private readonly projectsService = inject(ProjectsService);
  private readonly usersService = inject(UserService);
  private readonly tasksService = inject(TasksService);

  projectId = signal<number | null>(null);

  // señales para refrescar recursos
  private readonly refreshProject = signal(0);
  private readonly refreshUsers = signal(0);

  // resources para cargar datos con rxResource (Angular 21)
  readonly projectResource = rxResource({
    params: () => ({ id: this.projectId(), refresh: this.refreshProject() }),
    stream: () => {
      const id = this.projectId();
      if (!id) return of(null as ProjectResponse | null);
      return this.projectsService.getProjectById(id);
    },
  });

  readonly usersResource = rxResource({
    params: () => this.refreshUsers(),
    stream: () => this.usersService.getUsers(),
  });

  // vistas derivadas de los resources
  project = computed<ProjectResponse | null>(() => this.projectResource.value() ?? null);
  tasks = computed<TasksResponse[]>(() => this.project()?.tasks ?? []);
  users = computed<UserInterface[]>(() => this.usersResource.value() ?? []);

  selectedUserIds = signal<number[]>([]);
  showCreateTask = signal<boolean>(false);

  // formulario simple para crear tarea (usado con ngModel en la plantilla)
  newTaskName = '';
  newTaskDescription = '';
  newTaskDeadline = '';
  newTaskPriority: 'LOW' | 'MEDIUM' | 'HIGH' = 'LOW';

  hasError = signal(false);
  isPosting = signal(false);

  readonly hasProject = computed(() => this.project() !== null);
  readonly selectedUsers = computed(() => {
    const selected = new Set(this.selectedUserIds());
    return this.users().filter((u) => selected.has(u.id));
  });

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');
    const id = idParam ? Number(idParam) : null;

    if (!id) {
      return;
    }

    this.projectId.set(id);
  }

  toggleUser(userId: number): void {
    const current = this.selectedUserIds();
    if (current.includes(userId)) {
      this.selectedUserIds.set(current.filter((id) => id !== userId));
    } else {
      this.selectedUserIds.set([...current, userId]);
    }
  }

  createTask(): void {
    const projectId = this.projectId();
    if (!projectId) return;

    // nombre y fecha límite son obligatorios; descripción y prioridad pueden venir por defecto
    if (!this.newTaskName || !this.newTaskDeadline) {
      this.hasError.set(true);
      setTimeout(() => this.hasError.set(false), 2000);
      return;
    }

    this.isPosting.set(true);

    // El input type="date" devuelve 'YYYY-MM-DD', pero el backend espera LocalDateTime.
    // Convertimos a 'YYYY-MM-DDT00:00:00' para que Jackson pueda deserializarlo.
    const rawDeadline = this.newTaskDeadline;
    const deadlineIso =
      rawDeadline && rawDeadline.length === 10
        ? `${rawDeadline}T00:00:00`
        : rawDeadline;

    // Mapear prioridad a IDs del backend
    const priorityIdMap: Record<'LOW' | 'MEDIUM' | 'HIGH', number> = {
      LOW: 1,
      MEDIUM: 2,
      HIGH: 3,
    };

    const payload = {
      name: this.newTaskName,
      description: this.newTaskDescription,
      deadline: deadlineIso,
      stateProjectTaskId: 1,
      priorityId: priorityIdMap[this.newTaskPriority],
      projectId,
    };

    this.tasksService.createTaskForProject(projectId, payload).subscribe({
      next: () => {
        this.newTaskName = '';
        this.newTaskDescription = '';
        this.newTaskDeadline = '';
        this.newTaskPriority = 'LOW';
        this.showCreateTask.set(false);
        this.refreshProject.update((v) => v + 1);
        this.isPosting.set(false);
      },
      error: () => {
        this.hasError.set(true);
        this.isPosting.set(false);
        setTimeout(() => this.hasError.set(false), 2000);
      },
    });
  }

  assignUsers(): void {
    const projectId = this.projectId();
    if (!projectId) return;

    const userIds = this.selectedUserIds();
    if (!userIds.length) return;

    this.projectsService.assignUsersToProject(projectId, userIds).subscribe({
      next: () => {
        this.refreshUsers.update((v) => v + 1);
      },
    });
  }
}

