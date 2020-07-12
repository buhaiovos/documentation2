import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { StateCertification } from "../../models/state-certification.model";
import { DropdownOption } from "../../models/dropdown-option.model";

@Injectable()
export class StateCertificationService {
  private baseUrl: string = 'http://localhost:8080/v2/state-certifications';
  private byIdUrl: (id: number) => string = id => `${this.baseUrl}/${id}`;
  private options: string = `${this.baseUrl}/enumerated`

  constructor(private http: HttpClient) {
  }

  getAll(): Observable<StateCertification[]> {
    return this.http.get<StateCertification[]>(this.baseUrl);
  }

  getById(id: number): Observable<StateCertification> {
    return this.http.get<StateCertification>(this.byIdUrl(id));
  }

  save(department: StateCertification): Observable<StateCertification> {
    return department.id
      ? this.http.put<StateCertification>(this.byIdUrl(department.id), department)
      : this.http.post<StateCertification>(this.baseUrl, department);
  }

  deleteById(id: number): Observable<void> {
    return this.http.delete<any>(this.byIdUrl(id));
  }

  getOptions(): Observable<DropdownOption[]> {
    return this.http.get<DropdownOption[]>(this.options);
  }
}
