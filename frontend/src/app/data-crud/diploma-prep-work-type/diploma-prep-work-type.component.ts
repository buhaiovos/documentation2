import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from "@angular/router";
import { Observable } from "rxjs";
import { flatMap } from "rxjs/operators";
import { Utils } from "../../util/utils";
import { DiplomaPrepWorkTypeService } from "./diploma-prep-work-type.service";
import { DiplomaPrepWorkType } from "../../models/diploma-prep-work-type.model";

@Component({
  selector: 'app-diploma-prep-work-type',
  templateUrl: './diploma-prep-work-type.component.html',
  styleUrls: ['./diploma-prep-work-type.component.css'],
  providers: [ DiplomaPrepWorkTypeService ]
})
export class DiplomaPrepWorkTypeComponent implements OnInit {

  workType: DiplomaPrepWorkType;

  constructor(private router: Router,
              private route: ActivatedRoute,
              private service: DiplomaPrepWorkTypeService) {
  }

  ngOnInit(): void {
    const fetchFunction: (id: number) => Observable<DiplomaPrepWorkType> = id => this.service.getById(id);
    const createFunction: () => DiplomaPrepWorkType = () => DiplomaPrepWorkType.empty();
    this.route.paramMap
      .pipe(
        flatMap(params => Utils.fetchOrCreate$(fetchFunction, createFunction, params.get('id')))
      )
      .subscribe(workType => this.workType = workType);
  }

  save(): void {
    Utils.takeOne$(
      this.service.save(this.workType)
    ).subscribe(
      () => this.router.navigate(['/diploma-preparation/work-types'])
    )
  }

}
