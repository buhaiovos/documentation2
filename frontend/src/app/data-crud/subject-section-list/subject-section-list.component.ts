import { Component, OnInit } from '@angular/core';
import { SubjectSection } from "../../models/subject-section.model";
import { SubjectSectionService } from "./subject-section.service";
import { Router } from "@angular/router";
import { Utils } from "../../util/utils";
import { take } from "rxjs/operators";

@Component({
  selector: 'app-subject-section',
  templateUrl: './subject-section-list.component.html',
  styleUrls: ['./subject-section-list.component.css'],
  providers: [SubjectSectionService]
})
export class SubjectSectionListComponent implements OnInit {

  sections: SubjectSection[];

  constructor(private router: Router,
              private service: SubjectSectionService) {
  }

  ngOnInit(): void {
    Utils.takeOne$(
      this.service.getAll()
    ).subscribe(sections => this.sections = sections);
  }

  create() {
    this.router.navigate(['/section']).then(Utils.noopFunction);
  }

  edit(id: number) {
    this.router.navigate(['/section', {id: id}]).then(Utils.noopFunction);
  }

  delete(id: number) {
    this.service.deleteById(id)
      .pipe(take(1))
      .subscribe(() => this.removeFromListById(id));
  }

  private removeFromListById(id: number) {
    this.sections = this.sections.filter(section => section.id !== id);
  }
}
