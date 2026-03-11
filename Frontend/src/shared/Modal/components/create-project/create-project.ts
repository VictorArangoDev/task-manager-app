import { Component, EventEmitter, inject, Output, signal } from '@angular/core';
import { ModalService } from '../../modal.service';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { ProjectsService } from '../../../../task-manager/pages/services/projects.service';

@Component({
  selector: 'app-create-project',
  imports: [ReactiveFormsModule],
  templateUrl: './create-project.html',
})
export class CreateProject {

  modalService = inject(ModalService);
  @Output()
  projectCreated = new EventEmitter<void>();

  fb = inject(FormBuilder);
  projectsService = inject(ProjectsService);

  hasError = signal(false);
  isPosting = signal(false);

  projectForm = this.fb.group({
    name: ['', [Validators.required, Validators.minLength(4)]],
    description: ['', [Validators.required]],
    startDate: ['', [Validators.required]],
    deadline: ['', [Validators.required]],
    stateProjectTaskId: [1, [Validators.required]],
  });

  onSubmit() {

    if (this.projectForm.invalid) {
      this.hasError.set(true);

      setTimeout(() => {
        this.hasError.set(false);
      }, 2000);

      return;
    }

    this.isPosting.set(true);

    const formValue = this.projectForm.value;

    const project = {
      ...formValue,
      startDate: new Date(formValue.startDate!),
      deadline: new Date(formValue.deadline!)
    };

    this.projectsService.createProject(project).subscribe({

      next: () => {

        // recargar proyectos en el padre
        this.projectCreated.emit();

        // cerrar modal
        this.modalService.close();

        // reset formulario
        this.projectForm.reset({
          stateProjectTaskId: 1

        });

        this.isPosting.set(false);
      },

      error: (err) => {

        console.error(err);

        this.hasError.set(true);
        this.isPosting.set(false);

        setTimeout(() => {
          this.hasError.set(false);
        }, 2000);

      }

    });

  }
}
