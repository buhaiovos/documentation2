import { Component, OnInit } from '@angular/core';
import { DropdownOption } from "../../models/dropdown-option.model";
import { DiplomaPreparation } from "../../models/diploma-prep.model";
import { DiplomaPreparationService } from "./diploma-preparation.service";
import { DepartmentService } from "../department/department.service";
import { ActivatedRoute, ParamMap, Router } from "@angular/router";
import { DiplomaPrepWorkTypeService } from "../diploma-prep-work-type/diploma-prep-work-type.service";
import { flatMap, take } from "rxjs/operators";
import { forkJoin, Observable } from "rxjs";
import { Utils } from "../../util/utils";
import { WorkingPlanService } from "../working-plan/working-plan.service";

@Component({
  selector: 'app-diploma-preparation',
  templateUrl: './diploma-preparation.component.html',
  styleUrls: ['./diploma-preparation.component.css'],
  providers: [
    DiplomaPreparationService,
    DepartmentService,
    DiplomaPrepWorkTypeService,
    WorkingPlanService
  ]
})
export class DiplomaPreparationComponent implements OnInit {

  public diplomaPreparation: DiplomaPreparation;
  public workTypes: DropdownOption[];
  public departments: DropdownOption[];


  constructor(private diplomaPreparationService: DiplomaPreparationService,
              private departmentService: DepartmentService,
              private diplomaPreparationWorkTypeService: DiplomaPrepWorkTypeService,
              private workingPlanService: WorkingPlanService,
              private route: ActivatedRoute,
              private router: Router) {
  }

  ngOnInit(): void {
    this.route.paramMap.pipe(
      flatMap(param => this.getDiplomaPreparation(param)),
      take(1)
    ).subscribe(
      preparation => this.diplomaPreparation = preparation
    );
  }

  save(): void {
    const preparation$ = this.diplomaPreparationService.save(this.diplomaPreparation);
    const workPlanId$ = this.route.paramMap.pipe(flatMap(param => param.get('workingPlanId')), take(1));

    forkJoin({
      workPlanId: workPlanId$,
      preparation: preparation$
    }).pipe(
      flatMap(workPlanIdAndPreparation => this.workingPlanService.addDiplomaPreparation(
          +workPlanIdAndPreparation.workPlanId,
          new DropdownOption(workPlanIdAndPreparation.preparation.id, '')
        )
      )
    ).subscribe(workingPlan => {
      this.router.navigate(['/working-plan', {id: workingPlan.id}]).then(Utils.noopFunction)
    });
  }


  private getDiplomaPreparation(param: ParamMap): Observable<DiplomaPreparation> {
    const id = param.get('id');
    return id
      ? this.diplomaPreparationService.getById(+id)
      : this.diplomaPreparationService.save(DiplomaPreparation.empty())
  }
}
