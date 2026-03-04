import { Routes } from "@angular/router";
import { Layout } from "../components/layout/layout";
import { DashboardPage } from "./pages/dashboard-page/dashboard-page";
import authRoutes from "../auth/auth.routes";
import { ProjectsPage } from "./pages/projects-page/projects-page";
import { SettingsPage } from "./pages/settings-page/settings-page";
import { TaskListPage } from "./pages/task-list--page/task-list--page";
import { TeamsPage } from "./pages/teams-page/teams-page";

export const tasksRoutes: Routes = [
  {
    path: 'tasks',
    component: Layout,
    children: [
      {
        path: "dashboard", component: DashboardPage
      },
      {
        path: "projects", component: ProjectsPage
      },
      {
        path: "tasks", component: TaskListPage
      },
      {
        path: "teams", component: TeamsPage
      },
      {
        path: "settings", component: SettingsPage
      },
    ]
  }
]


export default tasksRoutes;
