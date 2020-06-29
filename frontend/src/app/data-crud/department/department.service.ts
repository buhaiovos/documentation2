import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { Department } from "../../models/department.model";

@Injectable()
export class DepartmentService {
  private baseUrl: string = 'http://localhost:8080/v2/departments';
  private byIdUrl: (id: number) => string = id => `${this.baseUrl}/${id}`;

  constructor(private http: HttpClient) {
  }

  getAll(): Observable<Department[]> {
    return this.http.get<Department[]>(this.baseUrl);
  }

  getById(id: number): Observable<Department> {
    return this.http.get<Department>(this.byIdUrl(id));
  }

  save(department: Department): Observable<Department> {
    return department.id
      ? this.http.put<Department>(this.byIdUrl(department.id), department)
      : this.http.post<Department>(this.baseUrl, department);
  }

  deleteById(id: number): Observable<void> {
    return this.http.delete<any>(this.byIdUrl(id));
  }
}
