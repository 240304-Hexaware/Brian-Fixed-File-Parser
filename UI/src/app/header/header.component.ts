import { Component } from '@angular/core';
import { AuthService } from '../auth/auth.service';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [RouterModule, CommonModule],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css',
})
export class HeaderComponent {
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
