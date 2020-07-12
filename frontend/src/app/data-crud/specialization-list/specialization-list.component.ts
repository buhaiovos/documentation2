import { Component, OnInit } from '@angular/core';
import { Router } from "@angular/router";
import { Utils } from "../../util/utils";
import { take } from "rxjs/operators";
import { Specialization } from "../../models/specialization.model";
import { SpecializationService } from "../specialization/specialization.service";

@Component({
  selector: 'app-specialization-list',
  templateUrl: './specialization-list.component.html',
  styleUrls: ['./specialization-list.component.css'],
  providers: [SpecializationService]
})
export class SpecializationListComponent implements OnInit {

  specializations: Specialization[];

  constructor(private router: Router,
              private service: SpecializationService) {
  }

  ngOnInit(): void {
    Utils.takeOne$(
      this.service.getAll()
    ).subscribe(departments => this.specializations = departments)
  }

  create() {
    this.router.navigate(['/specialization']).then(Utils.noopFunction);
  }

  edit(id: number) {
    this.router.navigate(['/specialization', {id: id}]).then(Utils.noopFunction);
  }

  delete(id: number) {
    this.service.deleteById(id)
      .pipe(take(1))
      .subscribe(() => this.removeFromList(id));
  }

  private removeFromList(id: number) {
    this.specializations = this.specializations.filter(d => d.id !== id);
  }

}
