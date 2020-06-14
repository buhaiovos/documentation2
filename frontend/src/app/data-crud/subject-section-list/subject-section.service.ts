import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { SubjectSection } from "../../models/subject-section.model";
import { Observable } from "rxjs";

@Injectable()
export class SubjectSectionService {
  private baseUrl: string = 'http://localhost:8080/v2/sections';
  private byIdUrl: (id: number) => string = id => `${this.baseUrl}/${id}`;

  constructor(private http: HttpClient) {
  }

  public getById(id: number): Observable<SubjectSection> {
    return this.http.get<SubjectSection>(this.byIdUrl(id));
  }

  public getAll(): Observable<SubjectSection[]> {
    return this.http.get<SubjectSection[]>(this.baseUrl);
  }

  public update(section: SubjectSection): Observable<SubjectSection> {
    return this.http.put<SubjectSection>(this.byIdUrl(section.id), section);
  }

  public create(section: SubjectSection): Observable<SubjectSection> {
    return this.http.post<SubjectSection>(this.baseUrl, section);
  }

  public deleteById(id: number): Observable<void> {
    return this.http.delete<void>(this.byIdUrl(id));
  }
}
