import { Routes } from '@angular/router';
import { AdminComponent } from './admin/admin.component';
import { AuthComponent } from './auth/auth.component';
import { NavComponent } from './nav/nav.component';
import { HomeComponent } from './home/home.component';

export const routes: Routes = [
  { path: 'nav', component: NavComponent },
  { path: 'admin', component: AdminComponent },
  { path: 'auth', component: AuthComponent },
  { path: 'home', component: HomeComponent },
];
