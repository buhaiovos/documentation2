import { Component, OnInit } from '@angular/core';
import { Router } from "@angular/router";
import { Utils } from "../../util/utils";
import { take } from "rxjs/operators";
import { DiplomaPrepWorkTypeService } from "../diploma-prep-work-type/diploma-prep-work-type.service";
import { DiplomaPrepWorkType } from "../../models/diploma-prep-work-type.model";

@Component({
  selector: 'app-diploma-prep-work-type-list',
  templateUrl: './diploma-prep-work-type-list.component.html',
  styleUrls: ['./diploma-prep-work-type-list.component.css'],
  providers: [DiplomaPrepWorkTypeService]
})
export class DiplomaPrepWorkTypeListComponent implements OnInit {
  workTypes: DiplomaPrepWorkType[];

  constructor(private router: Router,
              private service: DiplomaPrepWorkTypeService) {
  }

  ngOnInit(): void {
    Utils.takeOne$(
      this.service.getAll()
    ).subscribe(workTypes => this.workTypes = workTypes)
  }

  create() {
    this.router.navigate(['/diploma-preparation/work-type']).then(Utils.noopFunction);
  }

  edit(id: number) {
    this.router.navigate(['/diploma-preparation/work-type', {id: id}]).then(Utils.noopFunction);
  }

  delete(id: number) {
    this.service.deleteById(id)
      .pipe(take(1))
      .subscribe(() => this.removeFromList(id));
  }

  private removeFromList(id: number) {
    this.workTypes = this.workTypes.filter(d => d.id !== id);
  }
}
