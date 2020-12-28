import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, ParamMap, Router } from "@angular/router";
import { WorkingPlanService } from "./working-plan.service";
import { WorkingPlan } from "../../models/working-plan.model";
import { CurriculumService } from "../curriculum/curriculum.service";
import { DropdownOption } from "../../models/dropdown-option.model";
import { flatMap, map, take } from "rxjs/operators";
import { forkJoin, Observable } from "rxjs";
import { Utils } from "../../util/utils";
import { PracticeService } from "../practice/practice.service";

@Component({
  selector: 'app-working-plan',
  templateUrl: './working-plan.component.html',
  styleUrls: ['./working-plan.component.css'],
  providers: [WorkingPlanService, CurriculumService, PracticeService]
})
export class WorkingPlanComponent implements OnInit {

  public workingPlan: WorkingPlan;

  public curricula: DropdownOption[];
  public stateCertifications: DropdownOption[];
  public practices: DropdownOption[];
  public groups: DropdownOption[];
  public scientificResearchSubjects: DropdownOption[];

  constructor(private route: ActivatedRoute,
              private router: Router,
              private workingPlanService: WorkingPlanService,
              private curriculumService: CurriculumService,
              private practiceService: PracticeService) {
  }

  ngOnInit(): void {
    const workingPlan$ = this.route.paramMap.pipe(
      flatMap(param => this.getWorkingPlan(param)),
      take(1),
      map(wp => WorkingPlanComponent.lightweight(wp))
    );
    const practices$ = Utils.takeOne$(this.practiceService.getOptions());

    forkJoin({
      workingPlan: workingPlan$,
      practices: practices$
    }).subscribe(res => {
      this.workingPlan = WorkingPlanComponent.lightweight(res.workingPlan);
      this.practices = res.practices;
    });
    //   .subscribe(
    //   wp => this.workingPlan = WorkingPlanComponent.lightweight(wp)
    // );
  }

  save(): void {
    Utils.takeOne$(
      this.workingPlanService.save(this.workingPlan)
    ).subscribe(
      () => this.router.navigate(['/working-plans'])
    );
  }

  private getWorkingPlan(param: ParamMap): Observable<WorkingPlan> {
    const id = param.get('id');
    return id ? this.workingPlanService.getById(+id) : this.workingPlanService.save(WorkingPlan.empty())
  }

  editPreparation(preparationId: number): void {
    this.router.navigate(['/diploma-preparation', {id: preparationId}])
      .then(Utils.noopFunction);
  }

  removePreparation(preparationId: number): void {
    this.workingPlan.diplomaPreparations =
      this.workingPlan.diplomaPreparations.filter(dp => dp.id !== preparationId);
  }

  addDiplomaPreparation(workingPlanId: number): void {
    this.router.navigate(['/diploma-preparation', {workingPlanId: workingPlanId}])
      .then(Utils.noopFunction);
  }

  private static lightweight(wp: WorkingPlan): WorkingPlan {
    const lightweight = WorkingPlan.empty();
    lightweight.id = wp.id;
    lightweight.denotation = wp.denotation;
    lightweight.diplomaPreparations = wp.diplomaPreparations;
    lightweight.practice = wp.practice
    return lightweight;
  }
}
