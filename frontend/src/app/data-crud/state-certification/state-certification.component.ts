import { Component, OnInit } from '@angular/core';
import { StateCertification } from "../../models/state-certification.model";
import { StateCertificationService } from "./state-certification.service";
import { Utils } from "../../util/utils";
import { flatMap } from "rxjs/operators";
import { Observable } from "rxjs";
import { ActivatedRoute, Router } from "@angular/router";

@Component({
  selector: 'app-state-certification',
  templateUrl: './state-certification.component.html',
  styleUrls: ['./state-certification.component.css'],
  providers: [StateCertificationService]
})
export class StateCertificationComponent implements OnInit {

  certification: StateCertification;

  constructor(private router: Router,
              private route: ActivatedRoute,
              private service: StateCertificationService) {
  }

  ngOnInit(): void {
    const fetchFunction: (id: number) => Observable<StateCertification> = id => this.service.getById(id);
    const createFunction: () => StateCertification = () => StateCertification.empty();
    this.route.paramMap
      .pipe(
        flatMap(params => Utils.fetchOrCreate$(fetchFunction, createFunction, params.get('id')))
      )
      .subscribe(certification => this.certification = certification);
  }

  save(): void {
    Utils.takeOne$(
      this.service.save(this.certification)
    ).subscribe(
      () => this.router.navigate(['/certifications'])
    )
  }
}
