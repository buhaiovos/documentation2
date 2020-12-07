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
              private workTypeService: DiplomaPrepWorkTypeService,
              private workingPlanService: WorkingPlanService,
              private route: ActivatedRoute,
              private router: Router) {
  }

  ngOnInit(): void {
    const preparation$ = this.route.paramMap.pipe(
      flatMap(param => this.getDiplomaPreparation(param)),
      take(1)
    );
    const workTypes$ = this.workTypeService.getOptions();
    const departments$ = this.departmentService.getOptions();

    forkJoin({
      preparation: preparation$,
      workTypes: workTypes$,
      departments: departments$
    }).subscribe(sources => {
        this.diplomaPreparation = sources.preparation;
        this.departments = sources.departments;
        this.workTypes = sources.workTypes;
      }
    );
  }

  save(): void {
    const preparation$ = Utils.takeOne$(this.diplomaPreparationService.save(this.diplomaPreparation));
    const workPlanId$ = Utils.takeOne$(this.route.paramMap.pipe(flatMap(param => param.get('workingPlanId'))));

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
