import { Component } from '@angular/core';
import { HomeService } from './home.service';
import { AuthService } from '../auth/auth.service';
import { HttpClient } from '@angular/common/http';
import { Subscription, take } from 'rxjs';
import { NgIf } from '@angular/common';

// gets data from home service and displays it
@Component({
  selector: 'app-home',
  standalone: true,
  imports: [NgIf],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
})
export class HomeComponent {
  information = 'Welcome to the home page!';
  username: string | null = null;
  selectedSpecFile: File | null = null;
  selectedFlatFile: File | null = null;
  private userSub: Subscription | null = null;
  mergedData: string | null = null;

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

  onFlatFileSelected(event: any) {
    this.selectedFlatFile = <File>event.target.files[0];
    console.log('Selected: ', this.selectedFlatFile);
  }
  onSpecFileSelected(event: any) {
    this.selectedSpecFile = <File>event.target.files[0];
    console.log('Selected: ', this.selectedSpecFile);
  }
  submitFlatFile() {
    console.log('Submitted Flat File: ', this.selectedFlatFile);
    if (this.selectedFlatFile) {
      this.homeService.submitFlatFile(this.selectedFlatFile);
    }
  }
  submitSpecFile() {
    console.log('Submitted Spec File: ', this.selectedSpecFile);
    if (this.selectedSpecFile) {
      this.homeService.submitSpecFile(this.selectedSpecFile);
    }
  }

  submitMerge() {
    if (this.selectedFlatFile && this.selectedSpecFile) {
      this.homeService.submitMerger(
        this.selectedFlatFile?.name,
        this.selectedSpecFile?.name
      );
    }
  }
}
