import { Routes } from "@angular/router";
import { AuthLayout } from "./layout/auth-layout/auth-layout";
import { LoginPage } from "./pages/login/login";
import { SignupPage } from "./pages/signup/signup";

export const authRoutes:Routes =[
  {
    path: '',
    component: AuthLayout,
    children:[
      {
        path:'login',
        component:LoginPage,
      },
      {
        path:'register',
        component:SignupPage,
      },
      {
        path:'**',
        redirectTo: 'login'
      }
    ]
  }
]


export default authRoutes;
