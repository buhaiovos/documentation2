import { Component, OnInit } from '@angular/core';
import { SubjectListService } from "./subject-list.service";
import { Subject } from "../../models/subject-model";
import { SubjectHeader } from "../../models/subject-header.model";
import { Router } from "@angular/router";
import { Control } from "../../models/control.model";
import { take } from "rxjs/operators";
import { Utils } from "../../util/utils";

@Component({
  selector: 'app-subject-list',
  templateUrl: './subject-list.component.html',
  styleUrls: ['./subject-list.component.css'],
  providers: [SubjectListService]
})
export class SubjectListComponent implements OnInit {

  constructor(private subjectListService: SubjectListService,
              private router: Router) {
  }

  subjects: Subject[] = [];

  ngOnInit(): void {
    this.subjectListService
      .fetchSubjectList()
      .subscribe(subjects => this.subjects.push(...subjects));
  }

  editInfo(infoId: number) {
    this.router.navigate(['/subject-infos', {id: infoId}]).then(Utils.noopFunction);
  }

  addInfo(headerId: number) {
    this.router.navigate(['/subject-infos', {headerId: headerId}]).then(Utils.noopFunction)
  }

  deleteInfo(infoId: number, subject: Subject) {
    this.subjectListService.deleteInfo(infoId)
      .pipe(take(1))
      .subscribe(() => {
        subject.infos = subject.infos.filter(info => info.id !== infoId);
      });
  }

  editHeader(headerId: number) {
    this.router.navigate(['/subject-headers', {id: headerId}]).then(Utils.noopFunction);
  }

  deleteHeader(header: SubjectHeader) {
    this.subjectListService.deleteHeader(header)
      .pipe(take(1))
      .subscribe(() => {
        this.subjects = this.subjects.filter(subject => subject.header.id !== header.id);
      });
  }

  createHeader() {
    this.router.navigate(['/subject-headers']).then(Utils.noopFunction);
  }

  renderControls(controls: Control[]): string {
    let listElements = '';
    for (const control of controls) {
      const name = `${control.name} - ${control.semester}; `;
      listElements += name;
    }
    return listElements;
  }
}
