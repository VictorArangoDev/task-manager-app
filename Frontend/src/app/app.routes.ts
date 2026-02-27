import { Routes } from '@angular/router';
import { TaskListPage } from '../task-manager/pages/task-list--page/task-list--page';
import { Layout } from '../components/layout/layout';
import { HomePage } from '../task-manager/pages/home-page/home-page';
import { DashboardPage } from '../task-manager/pages/dashboard-page/dashboard-page';
import { ProjectsPage } from '../task-manager/pages/projects-page/projects-page';
import { TeamsPage } from '../task-manager/pages/teams-page/teams-page';
import { SettingsPage } from '../task-manager/pages/settings-page/settings-page';

export const routes: Routes = [
  {
    path:"", component: HomePage
  },

  {
    path:"task", component: Layout,
    children:[
      {
        path:"dashboard", component: DashboardPage
      },
      {
        path:"projects", component:ProjectsPage
      },
      {
        path:"tasks", component:TaskListPage
      },
      {
        path:"teams", component:TeamsPage
      },
      {
        path:"settings", component:SettingsPage
      },
    ]
  },



];
