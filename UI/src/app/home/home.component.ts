import { Component, OnInit, OnDestroy } from '@angular/core';
import { HomeService } from './home.service';
import { AuthService } from '../auth/auth.service';
import { Observable, Subscription } from 'rxjs';
import { AsyncPipe, JsonPipe, NgIf, NgFor } from '@angular/common';

// gets data from home service and displays it
@Component({
  selector: 'app-home',
  standalone: true,
  imports: [NgIf, NgFor, AsyncPipe, JsonPipe],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
})
export class HomeComponent implements OnInit, OnDestroy {
  // User managment
  private userSubscription: Subscription = new Subscription();
  username: string | null = null;
  view: any = 'spec';
  records: any = [];
  users: any = [];
  searchedUser: any = [];
  specFiles: any = [];
  flatFiles: any = [];
  Object = Object;

  // File managment
  selectedSpecFile: File | null = null;
  selectedFlatFile: File | null = null;

  constructor(
    private authService: AuthService,
    private homeService: HomeService
  ) {}

  // subscribes to user on init
  ngOnInit(): void {
    this.userSubscription = this.authService.user.subscribe((user) => {
      if (user) {
        console.log('Username for currently logged in user: ', user.username);

        // username
        this.username = user.username;
      } else {
        this.username = null;
      }
    });
  }
  setView(view: string) {
    this.view = view;
  }

  onSpecFileSelected(event: any) {
    this.selectedSpecFile = <File>event.target.files[0];
    console.log('Selected: ', this.selectedSpecFile);
  }
  onFlatFileSelected(event: any) {
    this.selectedFlatFile = <File>event.target.files[0];
    console.log('Selected: ', this.selectedFlatFile);
  }

  // submits spec file
  submitSpecFile() {
    if (this.selectedSpecFile) {
      this.homeService.submitSpecFile(this.selectedSpecFile).subscribe({
        next: (response) => {
          // Handle the response if needed
          console.log('Spec file submitted successfully', response);
          // Fetch spec files after successful submission
          this.fetchSpecFilesForUser();
        },
        error: (error) => {
          // Handle any errors
          console.error('Error submitting spec file', error);
        },
      });
    }
  }
  fetchSpecFiles() {
    this.homeService.getSpecFiles().subscribe({
      next: (response) => {
        // Handle the response if needed
        console.log('Spec files fetched successfully', response);
        this.specFiles = response;
      },
      error: (error) => {
        // Handle any errors
        console.error('Error fetching spec files', error);
      },
    });
  }

  fetchSpecFilesForUser() {
    this.homeService.getSpecFilesForUser().subscribe({
      next: (response) => {
        // Handle the response if needed
        console.log('Spec files fetched successfully', response);
        this.specFiles = response;
      },
      error: (error) => {
        // Handle any errors
        console.error('Error fetching spec files', error);
      },
    });
  }

  // submits flat file with spec file
  submitFlatFile(specFileName: string) {
    console.log('Submitted Flat File: ', this.selectedFlatFile);
    if (this.selectedFlatFile) {
      // Call the service and subscribe to the Observable
      this.homeService
        .submitFlatFile(this.selectedFlatFile, specFileName)
        .subscribe({
          next: (response) => {
            // Handle the response if needed
            console.log('Flat file submitted successfully', response);
            // Fetch records after successful submission
            this.fetchFlatFilesForUser();
            this.fetchRecordsForUser();
          },
          error: (error) => {
            // Handle any errors
            console.error('Error submitting flat file', error);
          },
        });
    }
  }

  fetchFlatFiles() {
    this.homeService.getFlatFiles().subscribe({
      next: (response) => {
        // Handle the response if needed
        console.log('Flat files fetched successfully', response);
        this.flatFiles = response;
      },
      error: (error) => {
        // Handle any errors
        console.error('Error fetching flat files', error);
      },
    });
  }
  fetchFlatFilesForUser() {
    this.homeService.getFlatFilesForUser().subscribe({
      next: (response) => {
        // Handle the response if needed
        console.log('Flat files fetched successfully', response);
        this.flatFiles = response;
      },
      error: (error) => {
        // Handle any errors
        console.error('Error fetching flat files', error);
      },
    });
  }

  fetchRecordsForUser() {
    this.homeService.getRecordsForUser().subscribe({
      next: (response) => {
        // Handle the response if needed
        console.log('Records fetched successfully', response);
        this.records = response;
      },
      error: (error) => {
        // Handle any errors
        console.error('Error fetching records', error);
      },
    });
  }

  fetchRecords() {
    this.homeService.getRecords().subscribe({
      next: (response) => {
        // Handle the response if needed
        console.log('Records fetched successfully', response);
        this.records = response;
      },
      error: (error) => {
        // Handle any errors
        console.error('Error fetching records', error);
      },
    });
  }
  fetchRecordsByFileType(fileType: string) {
    this.homeService.getRecordsByFileType(fileType).subscribe({
      next: (response) => {
        // Handle the response if needed
        console.log('Records fetched successfully', response);
        this.records = response;
      },
      error: (error) => {
        // Handle any errors
        console.error('Error fetching records', error);
      },
    });
  }
  fetchRecordsBySpecName(fileName: string) {
    this.homeService.getRecordsBySpecName(fileName).subscribe({
      next: (response) => {
        // Handle the response if needed
        console.log('Records fetched successfully', response);
        this.records = response;
      },
      error: (error) => {
        // Handle any errors
        console.error('Error fetching records', error);
      },
    });
  }

  deleteRecord(id: string) {
    this.homeService.deleteRecord(id).subscribe({
      next: (response) => {
        // Handle the response if needed
        console.log('Record deleted successfully', response);
      },
      error: (error) => {
        // Handle any errors
        console.error('Error deleting record', error);
      },
    });
  }

  fetchAllUsers() {
    this.homeService.getAllUsers().subscribe({
      next: (response) => {
        // Handle the response if needed
        console.log('Users fetched successfully', response);
        this.users = response;
      },
      error: (error) => {
        // Handle any errors
        console.error('Error fetching users', error);
      },
    });
  }

  deleteUser(username: string) {
    this.homeService.deleteUser(username).subscribe({
      next: (response) => {
        // Handle the response if needed
        console.log('User deleted successfully', response);
        // Fetch all users after successful deletion
      },
      error: (error) => {
        // Handle any errors
        console.error('Error deleting user', error);
      },
    });
  }

  searchUser(username: string) {
    this.homeService.getUserByUsername(username).subscribe({
      next: (response) => {
        // Handle the response if needed
        console.log('User fetched successfully', response);
        this.searchedUser = response;
      },
      error: (error) => {
        // Handle any errors
        console.error('Error fetching user', error);
      },
    });
  }

  updateUserRole(username: string, role: string) {
    this.homeService.updateUserRole(username, role).subscribe({
      next: (response) => {
        // Handle the response if needed
        console.log('User role updated successfully', response);
        // Fetch all users after successful update
      },
      error: (error) => {
        // Handle any errors
        console.error('Error updating user role', error);
      },
    });
  }

  ngOnDestroy(): void {
    this.userSubscription.unsubscribe();
  }
}
