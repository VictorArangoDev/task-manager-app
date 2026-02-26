import { Routes } from '@angular/router';
import { TaskListPage } from '../task-manager/pages/task-list--page/task-list--page';
import { Layout } from '../components/layout/layout';
import { HomePage } from '../task-manager/pages/home-page/home-page';

export const routes: Routes = [
  {
    path:"", component: HomePage
  },

  {
    path:"task", component: Layout,
    children:[
      {
        path:"list", component: TaskListPage

      }
    ]
  },



];
