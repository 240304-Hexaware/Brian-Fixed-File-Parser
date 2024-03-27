import { Injectable } from '@angular/core';
import {
  HttpClient,
  HttpErrorResponse,
  HttpHeaders,
} from '@angular/common/http';
import { Router } from '@angular/router';
import { catchError, tap } from 'rxjs/operators';
import { throwError, BehaviorSubject } from 'rxjs';

import { User } from './user.model';

const baseurl = 'http://localhost:8080/';

export interface AuthResponseData {
  kind: string;
  idToken: string;
  username: string;
  role: string;
  refreshToken: string;
  expiresIn: string;
  registered?: boolean;
}

@Injectable({ providedIn: 'root' })
export class AuthService {
  user = new BehaviorSubject<User | null>(null);
  private tokenExpirationTimer: any;

  constructor(private http: HttpClient, private router: Router) {}

  signup(username: string, password: string) {
    return this.http
      .post<AuthResponseData>(
        `${baseurl}register`,
        {
          username: username,
          password: password,
        },
        { observe: 'response' }
      )
      .pipe(
        catchError(this.handleError),
        tap((resData) => {
          const username = resData.body ? resData.body.username : null;
          const role = resData.body ? resData.body.role : null;
          const authorization = resData.headers.get('Authorization') as string;
          const expiresIn = resData.headers.get('expiresIn');
          if (expiresIn) {
            this.handleAuthentication(
              username as string,
              role as string,
              authorization as string,
              +expiresIn
            );
          }
        })
      );
  }

  login(username: string, password: string) {
    const base64 = btoa(`${username}:${password}`);
    const headers = new HttpHeaders({
      Authorization: `Basic ${base64}`,
    });
    return this.http
      .post<AuthResponseData>(
        `${baseurl}signin`,
        {},
        { headers: headers, observe: 'response' }
      )
      .pipe(
        catchError(this.handleError),
        tap((resData) => {
          const username = resData.body ? resData.body.username : null;
          const role = resData.body ? resData.body.role : null;
          const authorization = resData.headers.get('Authorization') as string;
          const expiresIn = resData.headers.get('expiresIn');
          if (expiresIn) {
            this.handleAuthentication(
              username as string,
              role as string,
              authorization as string,
              +expiresIn
            );
          }
        })
      );
  }

  autoLogin() {
    const userData: {
      username: string;
      role: string;
      _token: string;
      _tokenExpirationDate: string;
    } = JSON.parse(localStorage.getItem('userData') as string); // maybe error
    if (!userData) {
      return;
    }

    const loadedUser = new User(
      userData.username,
      userData.role,
      userData._token,
      new Date(userData._tokenExpirationDate)
    );

    if (loadedUser.token) {
      this.user.next(loadedUser);
      const expirationDuration =
        new Date(userData._tokenExpirationDate).getTime() -
        new Date().getTime();
      this.autoLogout(expirationDuration);
    }
  }

  logout() {
    this.user.next(null);
    this.router.navigate(['/auth']);
    localStorage.removeItem('userData');
    if (this.tokenExpirationTimer) {
      clearTimeout(this.tokenExpirationTimer);
    }
    this.tokenExpirationTimer = null;
  }

  autoLogout(expirationDuration: number) {
    this.tokenExpirationTimer = setTimeout(() => {
      this.logout();
    }, expirationDuration);
  }

  private handleAuthentication(
    username: string,
    role: string,
    token: string,
    expiresIn: number
  ) {
    const expirationDate = new Date(new Date().getTime() + expiresIn);
    const user = new User(username, role, token, expirationDate);
    this.user.next(user);
    this.autoLogout(expiresIn);
    localStorage.setItem('userData', JSON.stringify(user));
    console.log('This is the user: ', user);
  }

  private handleError(errorRes: HttpErrorResponse) {
    let errorMessage = 'An unknown error occurred!';
    if (!errorRes.error || !errorRes.error.error) {
      return throwError(() => errorMessage);
    }
    switch (errorRes.error.error.message) {
      case 'USER_EXISTS':
        errorMessage = 'This username exists already';
        break;
      case 'USER_NOT_FOUND':
        errorMessage = 'This username does not exist.';
        break;
      case 'INVALID_PASSWORD':
        errorMessage = 'This password is not correct.';
        break;
    }
    return throwError(() => errorMessage);
  }
}
