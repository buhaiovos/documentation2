import { ChangeDetectionStrategy, ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { Curriculum } from "../../models/curriculum.model";
import { ActivatedRoute, Router } from "@angular/router";
import { CurriculumService } from "../curriculum/curriculum.service";
import { RichSubjectInfo } from "../../models/rich-subject-info.model";
import { SubjectInfoService } from "../subject-info/subject-info.service";
import { flatMap, map, take } from "rxjs/operators";
import { forkJoin, Observable } from "rxjs";
import { WorkingPlanService } from "../working-plan/working-plan.service";
import { WorkingPlan } from "../../models/working-plan.model";
import { SubjectWithCipher } from "../../models/subject-with-cipher.model";

@Component({
  selector: 'app-curriculum-subject-selector',
  templateUrl: './curriculum-subject-selector.component.html',
  styleUrls: ['./curriculum-subject-selector.component.css'],
  providers: [CurriculumService, SubjectInfoService, WorkingPlanService],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class CurriculumSubjectSelectorComponent implements OnInit {

  curriculum: Curriculum | WorkingPlan;
  subjects: RichSubjectInfo[];

  constructor(private route: ActivatedRoute,
              private router: Router,
              private curriculumService: CurriculumService,
              private workingPlanService: WorkingPlanService,
              private subjectInfoService: SubjectInfoService,
              private changeDetector: ChangeDetectorRef) {
  }

  ngOnInit(): void {
    console.info('on init')
    const curriculum$ = this.route.paramMap.pipe(
      map(paramMap => new CurriculumSubjectSearchParams(
        paramMap.get('id'),
        paramMap.get('type')
      )),
      flatMap(params => this.fetchSubjects(params)),
      take(1)
    );
    const subjects$ = this.subjectInfoService.getAllEnriched();

    forkJoin({curriculum: curriculum$, subjects: subjects$})
      .subscribe(result => {
        this.curriculum = result.curriculum;
        this.subjects = result.subjects;
        this.filterSubjects();
        this.changeDetector.detectChanges();
      });
  }

  private fetchSubjects(params: CurriculumSubjectSearchParams): Observable<Curriculum | WorkingPlan> {
    const id = +(params.id);
    if (params.type) {
      return this.workingPlanService.getById(id)
    }
    return this.curriculumService.getById(id)
  }

  subjectSelected(subject: RichSubjectInfo): void {
    console.info("subject selected: " + subject.denotation);
    this.subjects = this.subjects.filter(
      subj => subj.id !== subject.id
    );
    const subjectWithCipher = new SubjectWithCipher(
      "",
      subject.id,
      subject.denotation,
      subject.semester,
      subject.ects,
      subject.lectures,
      subject.practices,
      subject.labs
    );
    this.curriculum.subjectsWithCiphers.push(subjectWithCipher)
  }

  save(): void {
    console.info("Saving changes to backend");
    this.curriculumService
      .save(this.curriculum)
      .pipe(take(1))
      .subscribe(_ => {
        console.info("Changes accepted (http 200)")
        this.ngOnInit()
      });
  }

  private filterSubjects(): void {
    this.subjects =
      this.subjects.filter(
        s => !this.isAlreadyIncluded(s)
      )
  }

  private isAlreadyIncluded(subject: RichSubjectInfo): boolean {
    const idsOfIncluded = this.curriculum.subjectsWithCiphers.map(s => s.id);
    return idsOfIncluded.includes(subject.id);
  }

  delete(id: number): void {
    console.info("Removing id: " + id);
    this.curriculum.subjectsWithCiphers =
      this.curriculum.subjectsWithCiphers.filter(s => s.id !== id);
  }
}

export class CurriculumSubjectSearchParams {
  constructor(public id: string,
              public type: string) {
  }
}
