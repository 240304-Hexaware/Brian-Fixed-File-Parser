import { Injectable } from '@angular/core';
import { AuthService } from '../auth/auth.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, Observable, of, switchMap } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class HomeService {
  mergedData: string | null = null;
  constructor(private http: HttpClient, private authService: AuthService) {}

  submitSpecFile(specFile: File): Observable<any> {
    return this.authService.user.pipe(
      switchMap((user) => {
        if (user) {
          const formData = new FormData();
          formData.append('file', specFile);
          const headers = new HttpHeaders({
            Authorization: user.token as string,
          });
          return this.http.post('http://localhost:8080/specfiles', formData, {
            headers: headers,
            responseType: 'text',
          });
        }
        return of(null);
      })
    );
  }
  getSpecFiles(): Observable<any> {
    return this.authService.user.pipe(
      switchMap((user) => {
        if (user) {
          const headers = new HttpHeaders({
            Authorization: user.token as string,
          });
          return this.http.get(`http://localhost:8080/specfiles`, {
            headers: headers,
          });
        }
        return of(null);
      })
    );
  }
  getSpecFilesForUser(): Observable<any> {
    return this.authService.user.pipe(
      switchMap((user) => {
        if (user) {
          const headers = new HttpHeaders({
            Authorization: user.token as string,
          });
          return this.http.get(
            `http://localhost:8080/specfiles/${user.username}`,
            {
              headers: headers,
            }
          );
        }
        return of(null);
      })
    );
  }
  submitFlatFile(flatFile: File, specFileName: string): Observable<any> {
    return this.authService.user.pipe(
      switchMap((user) => {
        if (user) {
          const formData = new FormData();
          formData.append('flatFile', flatFile);
          formData.append('specFileName', specFileName);
          const headers = new HttpHeaders({
            Authorization: user.token as string,
          });
          return this.http.post('http://localhost:8080/flatfiles', formData, {
            headers: headers,
            responseType: 'text',
          });
        }
        return of(null);
      })
    );
  }
  getFlatFiles(): Observable<any> {
    return this.authService.user.pipe(
      switchMap((user) => {
        if (user) {
          const headers = new HttpHeaders({
            Authorization: user.token as string,
          });
          return this.http.get(`http://localhost:8080/flatfiles`, {
            headers: headers,
          });
        }
        return of(null);
      })
    );
  }

  getFlatFilesForUser(): Observable<any> {
    return this.authService.user.pipe(
      switchMap((user) => {
        if (user) {
          const headers = new HttpHeaders({
            Authorization: user.token as string,
          });
          return this.http.get(
            `http://localhost:8080/flatfiles/${user.username}`,
            {
              headers: headers,
            }
          );
        }
        return of(null);
      })
    );
  }

  getRecordsForUser(): Observable<any> {
    return this.authService.user.pipe(
      switchMap((user) => {
        if (user) {
          const headers = new HttpHeaders({
            Authorization: user.token as string,
          });
          return this.http.get(
            `http://localhost:8080/records/${user.username}`,
            {
              headers: headers,
            }
          );
        }
        return of(null);
      })
    );
  }

  getRecords(): Observable<any> {
    return this.authService.user.pipe(
      switchMap((user) => {
        if (user) {
          const headers = new HttpHeaders({
            Authorization: user.token as string,
          });
          return this.http.get(`http://localhost:8080/records`, {
            headers: headers,
          });
        }
        return of(null);
      })
    );
  }

  getRecordsByFileType(fileType: string): Observable<any> {
    return this.authService.user.pipe(
      switchMap((user) => {
        if (user) {
          const headers = new HttpHeaders({
            Authorization: user.token as string,
          });
          return this.http.get(
            `http://localhost:8080/records/fileType/${fileType}`,
            {
              headers: headers,
            }
          );
        }
        return of(null);
      })
    );
  }
  getRecordsBySpecName(fileName: string): Observable<any> {
    return this.authService.user.pipe(
      switchMap((user) => {
        if (user) {
          const headers = new HttpHeaders({
            Authorization: user.token as string,
          });
          return this.http.get(
            `http://localhost:8080/records/spec/${fileName}`,
            {
              headers: headers,
            }
          );
        }
        return of(null);
      })
    );
  }

  deleteRecord(id: string): Observable<any> {
    return this.authService.user.pipe(
      switchMap((user) => {
        if (user) {
          const headers = new HttpHeaders({
            Authorization: user.token as string,
          });
          return this.http.delete(`http://localhost:8080/records/${id}`, {
            headers: headers,
          });
        }
        return of(null);
      })
    );
  }

  getAllUsers(): Observable<any> {
    return this.authService.user.pipe(
      switchMap((user) => {
        if (user) {
          const headers = new HttpHeaders({
            Authorization: user.token as string,
          });
          return this.http.get(`http://localhost:8080/users`, {
            headers: headers,
          });
        }
        return of(null);
      })
    );
  }
  getUserByUsername(username: string): Observable<any> {
    return this.authService.user.pipe(
      switchMap((user) => {
        if (user) {
          const headers = new HttpHeaders({
            Authorization: user.token as string,
          });
          return this.http.get(`http://localhost:8080/users/${username}`, {
            headers: headers,
          });
        }
        return of(null);
      })
    );
  }

  deleteUser(username: string): Observable<any> {
    return this.authService.user.pipe(
      switchMap((user) => {
        if (user) {
          const headers = new HttpHeaders({
            Authorization: user.token as string,
          });
          return this.http.delete(`http://localhost:8080/users/${username}`, {
            headers: headers,
          });
        }
        return of(null);
      })
    );
  }

  updateUserRole(username: string, role: string): Observable<any> {
    return this.authService.user.pipe(
      switchMap((user) => {
        if (user) {
          const headers = new HttpHeaders({
            Authorization: user.token as string,
          });
          const body = { username: username, role: role };
          return this.http.put(
            `http://localhost:8080/users/${username}`,
            body,
            {
              headers: headers,
            }
          );
        }
        return of(null);
      })
    );
  }
}
