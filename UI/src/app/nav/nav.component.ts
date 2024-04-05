import { Component } from '@angular/core';
import { AuthService } from '../auth/auth.service';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-nav',
  standalone: true,
  imports: [RouterModule, CommonModule],
  templateUrl: './nav.component.html',
  styleUrl: './nav.component.css',
})
export class NavComponent {
  isAdmin = false;
  constructor(private authService: AuthService) {}

  ngOnInit() {
    this.authService.user.subscribe((user) => {
      if (user) {
        this.isAdmin = user.role === 'ROLE_ADMIN';
        console.log('admin: ', this.isAdmin);
      }
    });
  }

  onLogout() {
    this.authService.logout();
  }
}
