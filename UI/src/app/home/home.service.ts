import { Injectable } from '@angular/core';
import { AuthService } from '../auth/auth.service';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable, Subscription } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class HomeService {
  mergedData: string | null = null;
  constructor(private http: HttpClient, private authService: AuthService) {}

  getData() {
    this.authService.user.subscribe((user) => {
      if (user) {
        const headers = new HttpHeaders({
          Authorization: user.token as string,
        });
        this.http
          .get('http://localhost:8080/users', {
            headers: headers,
          })
          .subscribe((data) => {
            console.log(data);
          });
      }
    });
  }
  submitFlatFile(file: File) {
    this.authService.user.subscribe((user) => {
      if (user) {
        const formData = new FormData();
        formData.append('file', file);
        const headers = new HttpHeaders({
          Authorization: user.token as string,
        });
        this.http
          .post('http://localhost:8080/flatfiles', formData, {
            headers: headers,
            responseType: 'text',
          })
          .subscribe((data) => {
            console.log(data);
          });
      }
    });
  }

  submitSpecFile(file: File) {
    this.authService.user.subscribe((user) => {
      if (user) {
        const formData = new FormData();
        formData.append('file', file);
        const headers = new HttpHeaders({
          Authorization: user.token as string,
        });
        this.http
          .post('http://localhost:8080/specfiles', formData, {
            headers: headers,
            responseType: 'text',
          })
          .subscribe((data) => {
            console.log(data);
          });
      }
    });
  }

  submitMerger(flatFileName: string, specFileName: string) {
    this.authService.user.subscribe((user) => {
      if (user) {
        const headers = new HttpHeaders({
          Authorization: user.token as string,
        });
        let params = new HttpParams();
        params = params.append('flatFileName', flatFileName);
        params = params.append('specFileName', specFileName);
        this.http
          .post(
            'http://localhost:8080/mergefiles',
            {},
            {
              headers: headers,
              params: params,
            }
          )
          .subscribe((data) => {
            console.log(data);
          });
      }
    });
  }
}
