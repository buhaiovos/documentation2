import { Component, OnInit } from '@angular/core';
import { CurriculumService } from "../curriculum/curriculum.service";
import { Curriculum } from "../../models/curriculum.model";
import { Utils } from "../../util/utils";

@Component({
  selector: 'app-curriculum-list',
  templateUrl: './curriculum-list.component.html',
  styleUrls: ['./curriculum-list.component.css'],
  providers: [CurriculumService]
})
export class CurriculumListComponent implements OnInit {

  public curricula: Curriculum[];

  constructor(private service: CurriculumService) {
  }

  ngOnInit(): void {
    Utils.takeOne$(this.service.getAll())
      .subscribe(c => this.curricula = c);
  }

  edit(c: Curriculum): void {
  }

  delete(c: Curriculum): void {
  }

  addSubject(c: Curriculum) {
  }
}
