import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, ParamMap, Router } from "@angular/router";
import { WorkingPlanService } from "./working-plan.service";
import { WorkingPlan } from "../../models/working-plan.model";
import { CurriculumService } from "../curriculum/curriculum.service";
import { DropdownOption } from "../../models/dropdown-option.model";
import { flatMap, take } from "rxjs/operators";
import { Observable } from "rxjs";
import { Utils } from "../../util/utils";

@Component({
  selector: 'app-working-plan',
  templateUrl: './working-plan.component.html',
  styleUrls: ['./working-plan.component.css'],
  providers: [WorkingPlanService, CurriculumService]
})
export class WorkingPlanComponent implements OnInit {

  public workingPlan: WorkingPlan;

  public curricula: DropdownOption[];
  public stateCertifications: DropdownOption[];
  public practices: DropdownOption[];
  public groups: DropdownOption[];
  public scientificResearchSubjects: DropdownOption[];
  public diplomaPreparations: DropdownOption[]

  constructor(private route: ActivatedRoute,
              private router: Router,
              private workingPlanService: WorkingPlanService,
              private curriculumService: CurriculumService) {
  }

  ngOnInit(): void {
    const workingPlan$ = this.route.paramMap.pipe(
      flatMap(param => this.getWorkingPlan(param)),
      take(1)
    ).subscribe(
      wp => this.workingPlan = wp
    );
  }

  save(): void {

  }

  private getWorkingPlan(param: ParamMap): Observable<WorkingPlan> {
    const id = param.get('id');
    return id ? this.workingPlanService.getById(+id) : this.workingPlanService.save(WorkingPlan.empty())
  }

  editPreparation(preparationId: number) {

  }

  removePreparation(preparationId: number) {

  }

  addDiplomaPreparation(workingPlanId: number) {
    this.router.navigate(['/diploma-preparation', {workingPlanId: workingPlanId}])
      .then(Utils.noopFunction);
  }
}
