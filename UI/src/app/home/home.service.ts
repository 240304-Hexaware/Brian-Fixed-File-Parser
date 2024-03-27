import { Injectable } from '@angular/core';
import { AuthService } from '../auth/auth.service';
import { HttpClient } from '@angular/common/http';
import { Subscription } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class HomeService {
  constructor(private http: HttpClient, private authService: AuthService) {}

  getData() {
    this.authService.user.subscribe((user) => {
      if (user) {
        this.http
          .get('http://localhost:8080/users', {
            headers: {
              Authorization: user.token as string,
            },
          })
          .subscribe((data) => {
            console.log(data);
          });
      }
    });
  }
}
