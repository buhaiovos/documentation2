import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { SubjectHeader } from "../../models/subject-header.model";
import { Observable } from "rxjs";
import { DropdownOption } from "../../models/dropdown-option.model";
import { map } from "rxjs/operators";

@Injectable()
export class SubjectHeaderService {
  private headerByIdUrl = id => `http://localhost:8080/v2/subject-headers/${id}`;
  private headersUrl = 'http://localhost:8080/v2/subject-headers';
  private headersOptions = this.headersUrl + '/enumerated';

  public newDropdownOption: () => DropdownOption = () => new DropdownOption(null, null);

  constructor(private http: HttpClient) {
  }

  getById(id: number): Observable<SubjectHeader> {
    return this.http.get<SubjectHeader>(this.headerByIdUrl(id)).pipe(
      map(
        sh => new SubjectHeader(
          sh.id,
          sh.denotation,
          sh.superSubject ? sh.superSubject : this.newDropdownOption(),
          sh.curriculumSection ? sh.curriculumSection : this.newDropdownOption(),
          sh.workingPlanSection ? sh.workingPlanSection : this.newDropdownOption(),
          sh.department ? sh.department : this.newDropdownOption()
        )
      )
    );
  }

  save(subjectHeader: SubjectHeader): Observable<SubjectHeader> {
    return (subjectHeader.id)
      ? this.http.put<SubjectHeader>(this.headerByIdUrl(subjectHeader.id), subjectHeader)
      : this.http.post<SubjectHeader>(this.headersUrl, subjectHeader);
  }

  getDepartments(): Observable<DropdownOption[]> {
    return this.http.get<DropdownOption[]>('http://localhost:8080/v2/departments/enumerated');
  }

  getSections(): Observable<DropdownOption[]> {
    return this.http.get<DropdownOption[]>('http://localhost:8080/v2/sections/enumerated');
  }

  getSubjectHeaderOptions(): Observable<DropdownOption[]> {
    return this.http.get<DropdownOption[]>(this.headersOptions);
  }

}
