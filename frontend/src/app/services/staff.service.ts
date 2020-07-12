import { Injectable } from '@angular/core';
import { Staff } from '../models/staff.model';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class StaffService {
  private allStaff = 'http://localhost:8080/staff/all';

  constructor(private http: HttpClient) {
  }

  getAll(): Observable<Staff[]> {
    return this.http.get<Staff[]>(
      this.allStaff
    );
  }
}
