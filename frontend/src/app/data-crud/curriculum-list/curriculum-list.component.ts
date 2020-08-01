import { Component, OnInit } from '@angular/core';
import { CurriculumService } from "../curriculum/curriculum.service";
import { Curriculum } from "../../models/curriculum.model";
import { Utils } from "../../util/utils";
import { Router } from "@angular/router";

@Component({
  selector: 'app-curriculum-list',
  templateUrl: './curriculum-list.component.html',
  styleUrls: ['./curriculum-list.component.css'],
  providers: [CurriculumService]
})
export class CurriculumListComponent implements OnInit {

  public curricula: Curriculum[];

  constructor(private service: CurriculumService,
              private router: Router) {
  }

  ngOnInit(): void {
    Utils.takeOne$(this.service.getAll())
      .subscribe(c => this.curricula = c);
  }

  edit(c: Curriculum): void {
  }

  delete(c: Curriculum): void {
  }

  addSubject(c: Curriculum): void {
    this.router
      .navigate(['/curriculum-subject-list-selection', {id: c.id}])
      .then(Utils.noopFunction);
  }
}
