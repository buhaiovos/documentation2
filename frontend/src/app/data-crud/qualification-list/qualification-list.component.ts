import { Component, OnInit } from '@angular/core';
import { Router } from "@angular/router";
import { Utils } from "../../util/utils";
import { take } from "rxjs/operators";
import { Qualification } from "../../models/qualification.model";
import { QualificationService } from "../qualification/qualification.service";

@Component({
  selector: 'app-qualification-list',
  templateUrl: './qualification-list.component.html',
  styleUrls: ['./qualification-list.component.css'],
  providers: [QualificationService]
})
export class QualificationListComponent implements OnInit {

  qualifications: Qualification[];

  constructor(private router: Router,
              private service: QualificationService) {
  }

  ngOnInit(): void {
    Utils.takeOne$(
      this.service.getAll()
    ).subscribe(qualifications => this.qualifications = qualifications)
  }

  create(): void {
    this.router.navigate(['/qualification']).then(Utils.noopFunction);
  }

  edit(id: number): void {
    this.router.navigate(['/qualification', {id: id}]).then(Utils.noopFunction);
  }

  delete(id: number): void {
    this.service.deleteById(id)
      .pipe(take(1))
      .subscribe(() => this.removeFromListById(id));
  }

  private removeFromListById(id: number): void {
    this.qualifications = this.qualifications.filter(d => d.id !== id);
  }

}
