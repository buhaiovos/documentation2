import {Component, OnInit} from '@angular/core';
import {Subject} from "../../../../models/subject.model";

@Component({
  selector: 'app-subject-list',
  templateUrl: './subject-list.component.html',
  styleUrls: ['./subject-list.component.css']
})
export class SubjectListComponent implements OnInit {

  constructor() {
  }

  subjects: Subject[] = [];

  ngOnInit(): void {
    this.subjects.push(
      new Subject(1, "first subject", 21),
      new Subject(2, 'Second Subject', 22),
      new Subject(22, 'super subject', null)
    );
    console.log('Pushed')
    console.log(this.subjects)
  }

  edit(subject: Subject) {
    alert('editing')
  }

  delete(id: number) {
    this.subjects = this.subjects.filter(s => s.id !== id)
  }
}
