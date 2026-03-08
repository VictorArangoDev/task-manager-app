import { Routes } from '@angular/router';
import { HomePage } from '../home-page/pages/home-page/home-page';
import { NotAuthenticatedGuard } from '../auth/guards/not-authenticated.gard';

export const routes: Routes = [
  {
    path:"", component: HomePage
  },
  {
    path:"auth",
    loadChildren: () => import('../auth/auth.routes'),
    canMatch:[
      NotAuthenticatedGuard,

    ]
  },
  {
    path:'',
    loadChildren: () => import('../task-manager/task.routes')
  }
];
