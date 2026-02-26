import { Routes } from '@angular/router';
import { TaskListPage } from '../task-manager/pages/task-list--page/task-list--page';
import { Layout } from '../components/layout/layout';
import { HomePage } from '../task-manager/pages/home-page/home-page';
import { DashboardPage } from '../task-manager/pages/dashboard-page/dashboard-page';
import { ProjectsPage } from '../task-manager/pages/projects-page/projects-page';

export const routes: Routes = [
  {
    path:"", component: HomePage
  },

  {
    path:"task", component: Layout,
    children:[
      {
        path:"list", component: TaskListPage
      },
      {
        path:"dashboard", component: DashboardPage
      },
      {
        path:"projects", component:ProjectsPage
      }
    ]
  },



];
