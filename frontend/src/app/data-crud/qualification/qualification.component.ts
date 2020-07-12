import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from "@angular/router";
import { Observable } from "rxjs";
import { flatMap } from "rxjs/operators";
import { Utils } from "../../util/utils";
import { QualificationService } from "./qualification.service";
import { Qualification } from "../../models/qualification.model";

@Component({
  selector: 'app-qualification',
  templateUrl: './qualification.component.html',
  styleUrls: ['./qualification.component.css'],
  providers: [QualificationService]
})
export class QualificationComponent implements OnInit {

  qualification: Qualification;

  constructor(private router: Router,
              private route: ActivatedRoute,
              private service: QualificationService) {
  }

  ngOnInit(): void {
    const fetchFunction: (id: number) => Observable<Qualification> = id => this.service.getById(id);
    const createFunction: () => Qualification = () => Qualification.empty();
    this.route.paramMap
      .pipe(
        flatMap(params => Utils.fetchOrCreate$(fetchFunction, createFunction, params.get('id')))
      )
      .subscribe(qualification => this.qualification = qualification);
  }

  save(): void {
    Utils.takeOne$(
      this.service.save(this.qualification)
    ).subscribe(
      () => this.router.navigate(['/qualifications'])
    )
  }

}
