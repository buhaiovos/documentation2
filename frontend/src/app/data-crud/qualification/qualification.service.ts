import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { DropdownOption } from "../../models/dropdown-option.model";
import { Qualification } from "../../models/qualification.model";

@Injectable()
export class QualificationService {
  private baseUrl: string = 'http://localhost:8080/v2/qualifications';
  private byIdUrl: (id: number) => string = id => `${this.baseUrl}/${id}`;
  private options: string = `${this.baseUrl}/enumerated`

  constructor(private http: HttpClient) {
  }

  getAll(): Observable<Qualification[]> {
    return this.http.get<Qualification[]>(this.baseUrl);
  }

  getById(id: number): Observable<Qualification> {
    return this.http.get<Qualification>(this.byIdUrl(id));
  }

  save(qualification: Qualification): Observable<Qualification> {
    return qualification.id
      ? this.http.put<Qualification>(this.byIdUrl(qualification.id), qualification)
      : this.http.post<Qualification>(this.baseUrl, qualification);
  }

  deleteById(id: number): Observable<void> {
    return this.http.delete<any>(this.byIdUrl(id));
  }

  getOptions(): Observable<DropdownOption[]> {
    return this.http.get<DropdownOption[]>(this.options);
  }
}
