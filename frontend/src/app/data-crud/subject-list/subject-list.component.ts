import { Component, OnInit } from '@angular/core';
import { SubjectListService } from "./subject-list.service";
import { Subject } from "../../models/subject-model";
import { SubjectHeader } from "../../models/subject-header.model";
import { Router } from "@angular/router";
import { Control } from "../../models/control.model";

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
    this.router.navigate(['/subject-infos', {id: infoId}])
  }

  addInfo(headerId: number) {
    this.router.navigate(['/subject-infos', {headerId: headerId}])
  }

  deleteInfo(infoId: number) {
    this.subjectListService.deleteInfo(infoId)
      .subscribe(() => this.ngOnInit());
  }

  editHeader(headerId: number) {
    this.router.navigate(['/subject-headers', {id: headerId}]);
  }

  deleteHeader(header: SubjectHeader) {
    alert('deleting ' + header);
  }

  createHeader() {
    this.router.navigate(['/subject-headers']);
  }

  renderControls(controls: Control[]): string {
    let listElements = '';
    for (const control of controls) {
      const name = `${control.name} - ${control.semester}\n`;
      listElements += name;
    }
    return listElements;
  }
}
