import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { SubjectHeader } from "../../models/subject-header.model";
import { Observable } from "rxjs";
import { DropdownOption } from "../../models/dropdown-option.model";
import { map } from "rxjs/operators";
import { AbstractCrudService } from "../abstract/abstract-crud.service";
import { SubjectSectionService } from "../subject-section-list/subject-section.service";
import { DepartmentService } from "../department/department.service";

@Injectable()
export class SubjectHeaderService extends AbstractCrudService<SubjectHeader> {
  baseUrl: () => string = () => 'http://localhost:8080/v2/subject-headers';

  constructor(http: HttpClient,
              private sectionService: SubjectSectionService,
              private departmentService: DepartmentService) {
    super(http);
  }

  getById(id: number): Observable<SubjectHeader> {
    return this.http.get<SubjectHeader>(this.byIdUrl(id)).pipe(
      map(
        sh => new SubjectHeader(
          sh.id,
          sh.denotation,
          sh.superSubject ? sh.superSubject : DropdownOption.empty(),
          sh.curriculumSection ? sh.curriculumSection : DropdownOption.empty(),
          sh.workingPlanSection ? sh.workingPlanSection : DropdownOption.empty(),
          sh.department ? sh.department : DropdownOption.empty()
        )
      )
    );
  }

  getDepartments(): Observable<DropdownOption[]> {
    return this.departmentService.getOptions();
  }

  getSections(): Observable<DropdownOption[]> {
    return this.sectionService.getOptions();
  }

  static constructNewHeader(): SubjectHeader {
    return new SubjectHeader(
      undefined,
      'Введіть назву',
      DropdownOption.empty(),
      DropdownOption.empty(),
      DropdownOption.empty(),
      DropdownOption.empty()
    );
  }
}
