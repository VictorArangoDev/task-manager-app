import { Routes } from '@angular/router';
import { HomePage } from '../home-page/pages/home-page/home-page';

export const routes: Routes = [
  {
    path:"", component: HomePage
  },
  {
    path:"auth",
    loadChildren: () => import('../auth/auth.routes')
    //TODO: Guards
  },
  {
    path:'',
    loadChildren: () => import('../task-manager/task.routes')
  }
];
