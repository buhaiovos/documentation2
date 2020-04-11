import {Component, OnInit} from '@angular/core';
import {SubjectInfo} from "../../../../models/subject-info.model";
import {SubjectListService} from "./subject-list.service";
import {Subject} from "../../../../models/subject-model";
import {SubjectHeader} from "../../../../models/subject-header.model";

@Component({
  selector: 'app-subject-list',
  templateUrl: './subject-list.component.html',
  styleUrls: ['./subject-list.component.css'],
  providers: [SubjectListService]
})
export class SubjectListComponent implements OnInit {

  constructor(private subjectListService: SubjectListService) {
  }

  subjects: Subject[] = [];

  ngOnInit(): void {
    this.subjectListService
      .fetchSubjectList()
      .subscribe(subjects => this.subjects.push(...subjects));
  }

  editHeader(header: SubjectHeader) {
    alert('editing ' + header);
  }

  editInfo(info: SubjectInfo) {
    alert('editing ' + info);
  }

  deleteHeader(header: SubjectHeader) {
    alert('deleting ' + header);
  }

  deleteInfo(info: SubjectInfo) {
    alert('deleting ' + info);
  }

  addInfo(header: SubjectHeader) {
    alert('adding info ' + header);
  }
}
