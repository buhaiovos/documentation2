import { Component, OnInit } from '@angular/core';
import { Router } from "@angular/router";
import { Utils } from "../../util/utils";
import { take } from "rxjs/operators";
import { StateCertification } from "../../models/state-certification.model";
import { StateCertificationService } from "../state-certification/state-certification.service";

@Component({
  selector: 'app-state-certification-list',
  templateUrl: './state-certification-list.component.html',
  styleUrls: ['./state-certification-list.component.css'],
  providers: [StateCertificationService]
})
export class StateCertificationListComponent implements OnInit {

  certifications: StateCertification[];

  constructor(private router: Router,
              private service: StateCertificationService) {
  }

  ngOnInit(): void {
    Utils.takeOne$(
      this.service.getAll()
    ).subscribe(certifications => this.certifications = certifications)
  }

  create() {
    this.router.navigate(['/certification']).then(Utils.noopFunction);
  }

  edit(id: number) {
    this.router.navigate(['/certification', {id: id}]).then(Utils.noopFunction);
  }

  delete(id: number) {
    this.service.deleteById(id)
      .pipe(take(1))
      .subscribe(() => this.removeFromList(id));
  }

  private removeFromList(id: number) {
    this.certifications = this.certifications.filter(d => d.id !== id);
  }
}
