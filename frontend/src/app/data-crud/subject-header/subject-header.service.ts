import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { SubjectHeader } from "../../models/subject-header.model";
import { Observable } from "rxjs";

@Injectable()
export class SubjectHeaderService {
  private headerByIdUrl = id => `http://localhost:8080/v2/subject-headers/${id}`;
  private headersUrl = 'http://localhost:8080/v2/subject-headers';

  constructor(private http: HttpClient) {
  }

  getById(id: number): Observable<SubjectHeader> {
    return this.http.get<SubjectHeader>(this.headerByIdUrl(id));
  }

  save(subjectHeader: SubjectHeader): Observable<SubjectHeader> {
    return (subjectHeader.id)
      ? this.http.put<SubjectHeader>(this.headerByIdUrl(subjectHeader.id), subjectHeader)
      : this.http.post<SubjectHeader>(this.headersUrl, subjectHeader);
  }
}
