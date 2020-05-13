import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from "@angular/router";
import { flatMap, take } from "rxjs/operators";
import { SubjectHeaderService } from "./subject-header.service";
import { SubjectHeader } from "../../models/subject-header.model";
import { forkJoin, Observable, of } from "rxjs";
import { DropdownOption } from "../../models/dropdown-option.model";

@Component({
  selector: 'app-subject-header',
  templateUrl: './subject-header.component.html',
  styleUrls: ['./subject-header.component.css'],
  providers: [SubjectHeaderService]
})
export class SubjectHeaderComponent implements OnInit {
  subjectHeader: SubjectHeader;

  departments: DropdownOption[];
  headers: DropdownOption[];
  sections: DropdownOption[];

  private takeOne$: <T>(o: Observable<T>) => Observable<T> = (o) => o.pipe(take(1));

  constructor(private route: ActivatedRoute,
              private router: Router,
              private service: SubjectHeaderService) {
  }

  ngOnInit(): void {
    const subjectHeader$ = this.takeOne$(
      this.route.paramMap.pipe(flatMap(params => this.getSubjectHeader(params.get('id'))))
    );
    const departments$ = this.takeOne$(this.service.getDepartments());
    const headers$ = this.takeOne$(this.service.getSubjectHeaderOptions());
    const sections$ = this.takeOne$(this.service.getSections());

    forkJoin({
        header: subjectHeader$,
        departments: departments$,
        headers: headers$,
        sections: sections$
      }
    ).subscribe(result => {
        this.subjectHeader = result.header;
        this.departments = result.departments;
        this.headers = result.headers;
        this.sections = result.sections;
      }
    );

  }

  save(): void {
    this.takeOne$(this.service.save(this.subjectHeader))
      .subscribe(() => this.router.navigate(['subjects']));
  }

  private getSubjectHeader(id: string): Observable<SubjectHeader> {
    return (id)
      ? this.service.getById(+id)
      : of(this.getNewSubjectHeader());
  }

  private getNewSubjectHeader() {
    return new SubjectHeader(
      undefined,
      'Введіть назву',
      this.service.newDropdownOption(),
      this.service.newDropdownOption(),
      this.service.newDropdownOption(),
      this.service.newDropdownOption()
    );
  }
}
