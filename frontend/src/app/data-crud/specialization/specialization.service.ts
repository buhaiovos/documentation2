import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { Specialization } from "../../models/specialization.model";

@Injectable()
export class SpecializationService {
  public constructor(private http: HttpClient) {
  }

  private baseUrl: string = 'http://localhost:8080/v2/specializations';
  private byIdUrl: (id: number) => string = id => `${this.baseUrl}/${id}`;

  getAll(): Observable<Specialization[]> {
    return this.http.get<Specialization[]>(this.baseUrl);
  }

  getById(id: number): Observable<Specialization> {
    return this.http.get<Specialization>(this.byIdUrl(id));
  }

  save(specialization: Specialization): Observable<Specialization> {
    return specialization.id
      ? this.http.put<Specialization>(this.byIdUrl(specialization.id), specialization)
      : this.http.post<Specialization>(this.baseUrl, specialization);
  }

  deleteById(id: number): Observable<void> {
    return this.http.delete<any>(this.byIdUrl(id));
  }
}
