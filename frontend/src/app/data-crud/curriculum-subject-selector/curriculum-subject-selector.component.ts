import { Component, OnInit } from '@angular/core';
import { Curriculum, SubjectWithCipher } from "../../models/curriculum.model";
import { ActivatedRoute, Router } from "@angular/router";
import { CurriculumService } from "../curriculum/curriculum.service";
import { RichSubjectInfo } from "../../models/rich-subject-info.model";
import { SubjectInfoService } from "../subject-info/subject-info.service";
import { flatMap, map, take } from "rxjs/operators";
import { forkJoin } from "rxjs";

@Component({
  selector: 'app-curriculum-subject-selector',
  templateUrl: './curriculum-subject-selector.component.html',
  styleUrls: ['./curriculum-subject-selector.component.css'],
  providers: [CurriculumService, SubjectInfoService]
})
export class CurriculumSubjectSelectorComponent implements OnInit {

  curriculum: Curriculum;
  subjects: RichSubjectInfo[];

  constructor(private route: ActivatedRoute,
              private router: Router,
              private curriculumService: CurriculumService,
              private subjectInfoService: SubjectInfoService) {
  }

  ngOnInit(): void {
    const curriculum$ = this.route.paramMap.pipe(
      map(paramMap => paramMap.get('id')),
      flatMap(id => this.curriculumService.getById(+id)),
      take(1)
    );
    const subjects$ = this.subjectInfoService.getAllEnriched();

    forkJoin({curriculum: curriculum$, subjects: subjects$})
      .subscribe(result => {
        this.curriculum = result.curriculum;
        this.subjects = result.subjects;
        this.filterSubjects();
      });
  }

  subjectSelected(subject: RichSubjectInfo): void {
    console.log("subject selected: " + subject.denotation);
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
    console.log("Pushing changes");
    this.curriculumService
      .save(this.curriculum)
      .pipe(take(1))
      .subscribe(_ => {
        console.log("Changes accepted")
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
    console.log("Removing id: " + id);
    this.curriculum.subjectsWithCiphers =
      this.curriculum.subjectsWithCiphers.filter(s => s.id !== id);
  }
}
