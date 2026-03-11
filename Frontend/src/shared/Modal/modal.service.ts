import { Injectable, signal } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ModalService {

  private _isOpen = signal(false);
  private _modalType = signal<string | null>(null);

  isOpen = this._isOpen.asReadonly();
  modalType = this._modalType.asReadonly();

  open(type: string) {
    this._modalType.set(type);
    this._isOpen.set(true);
  }

  close() {
    this._isOpen.set(false);
    this._modalType.set(null);
  }

}
