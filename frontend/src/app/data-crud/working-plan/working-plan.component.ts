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
import { GroupService } from "../group/group.service";
import { StateCertificationService } from "../state-certification/state-certification.service";

@Component({
  selector: 'app-working-plan',
  templateUrl: './working-plan.component.html',
  styleUrls: ['./working-plan.component.css'],
  providers: [
    WorkingPlanService,
    CurriculumService,
    PracticeService,
    GroupService,
    StateCertificationService
  ]
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
              private practiceService: PracticeService,
              private groupService: GroupService,
              private stateCertificationService: StateCertificationService) {
  }

  ngOnInit(): void {
    const workingPlan$ = this.getWorkingPlan$();
    const practices$ = Utils.takeOne$(this.practiceService.getOptions());
    const groups$ = Utils.takeOne$(this.groupService.getOptions());
    const stateCertifications$ = Utils.takeOne$(this.stateCertificationService.getOptions());
    const curricula$ = Utils.takeOne$(this.curriculumService.getOptions());

    forkJoin({
      workingPlan: workingPlan$,
      practices: practices$,
      groups: groups$,
      stateCertifications: stateCertifications$,
      curricula: curricula$
    }).subscribe(res => {
      this.workingPlan = WorkingPlanComponent.lightweight(res.workingPlan);
      this.practices = res.practices;
      this.groups = res.groups;
      this.stateCertifications = res.stateCertifications;
      this.curricula = res.curricula;

      const subjects$ = Utils.takeOne$(this.workingPlanService.getWorkingPlanSubjects(res.workingPlan.id));
      subjects$.subscribe(subjects => this.scientificResearchSubjects = subjects);
    });
  }

  save(): void {
    Utils.takeOne$(
      this.workingPlanService.save(this.workingPlan)
    ).subscribe(
      () => this.router.navigate(['/working-plans'])
    );
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

  private getWorkingPlan$(): Observable<WorkingPlan> {
    return this.route.paramMap.pipe(
      flatMap(param => this.getWorkingPlan(param)),
      take(1),
      map(wp => WorkingPlanComponent.lightweight(wp))
    );
  }

  private getWorkingPlan(param: ParamMap): Observable<WorkingPlan> {
    const id = param.get('id');
    return id ? this.workingPlanService.getById(+id) : this.workingPlanService.save(WorkingPlan.empty())
  }

  private static lightweight(wp: WorkingPlan): WorkingPlan {
    const lightweight = WorkingPlan.empty();
    lightweight.id = wp.id;
    lightweight.denotation = wp.denotation;
    lightweight.curriculum = wp.curriculum;
    lightweight.diplomaPreparations = wp.diplomaPreparations;
    lightweight.practice = wp.practice;
    lightweight.scientificResearchSubject = wp.scientificResearchSubject;
    lightweight.stateCertification = wp.stateCertification;
    return lightweight;
  }
}
