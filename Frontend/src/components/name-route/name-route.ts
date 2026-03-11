import { TitleCasePipe } from '@angular/common';
import { Component, inject, signal } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';
import { filter } from 'rxjs';

@Component({
  selector: 'name-route',
  imports: [TitleCasePipe],
  templateUrl: './name-route.html',
})
export class NameRoute {

   router = inject(Router);

  currentRoute = signal(this.router.url)

  constructor() {
    this.router.events
      .pipe(filter(event => event instanceof NavigationEnd))
      .subscribe(() => {
       this.currentRoute.set(this.getRouteName(this.router.url));
      });
  }

   private getRouteName(url: string): string {
    const segments = url.split('/').filter(Boolean);
    return segments.at(-1) || '';
  }

 }
