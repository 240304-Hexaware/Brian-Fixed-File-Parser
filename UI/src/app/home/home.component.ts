import { Component } from '@angular/core';
import { HomeService } from './home.service';
import { AuthService } from '../auth/auth.service';
import { HttpClient } from '@angular/common/http';
import { Subscription, take } from 'rxjs';

// gets data from home service and displays it
@Component({
  selector: 'app-home',
  standalone: true,
  imports: [],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
})
export class HomeComponent {
  information = 'Welcome to the home page!';
  username: string | null = null;
  private userSub: Subscription | null = null;

  constructor(
    private http: HttpClient,
    private authService: AuthService,
    private homeService: HomeService
  ) {}
  // gets name of user from auth service
  getUsername() {
    this.authService.user.pipe(take(1)).subscribe((user) => {
      if (user) {
        console.log(user.username); // Access the username here
        this.username = user.username;
      }
    });
  }

  getUsers() {
    this.homeService.getData();
  }
}
