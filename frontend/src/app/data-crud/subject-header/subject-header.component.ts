import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from "@angular/router";
import { flatMap } from "rxjs/operators";
import { SubjectHeaderService } from "./subject-header.service";
import { SubjectHeader } from "../../models/subject-header.model";
import { forkJoin, Observable } from "rxjs";
import { DropdownOption } from "../../models/dropdown-option.model";
import { Utils } from "../../util/utils";
import { DepartmentService } from "../department/department.service";
import { SubjectSectionService } from "../subject-section-list/subject-section.service";

@Component({
  selector: 'app-subject-header',
  templateUrl: './subject-header.component.html',
  styleUrls: ['./subject-header.component.css'],
  providers: [SubjectHeaderService, DepartmentService, SubjectSectionService]
})
export class SubjectHeaderComponent implements OnInit {
  subjectHeader: SubjectHeader;

  departments: DropdownOption[];
  headers: DropdownOption[];
  sections: DropdownOption[];

  constructor(private route: ActivatedRoute,
              private router: Router,
              private service: SubjectHeaderService) {
  }

  ngOnInit(): void {
    const subjectHeader$ = Utils.takeOne$(this.getSubjectHeader());
    const departments$ = Utils.takeOne$(this.service.getDepartments());
    const headers$ = Utils.takeOne$(this.service.getOptions());
    const sections$ = Utils.takeOne$(this.service.getSections());

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
    Utils.takeOne$(
      this.service.save(this.subjectHeader)
    ).subscribe(() => this.router.navigate(['subjects']));
  }

  private getSubjectHeader(): Observable<SubjectHeader> {
    const fetchFunction: ((id: number) => Observable<SubjectHeader>) = id => this.service.getById(id);
    const createFunction: (() => SubjectHeader) = () => SubjectHeaderService.constructNewHeader();
    return this.route.paramMap.pipe(
      flatMap(params => Utils.fetchOrCreate$(fetchFunction, createFunction, params.get('id')))
    );
  }
}
