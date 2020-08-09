import { Component, OnInit } from '@angular/core';
import { Curriculum } from "../../models/curriculum.model";
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

  constructor(private activeRoute: ActivatedRoute,
              private router: Router,
              private curriculumService: CurriculumService,
              private subjectInfoService: SubjectInfoService) {
  }

  ngOnInit(): void {
    const curriculum$ = this.activeRoute.paramMap.pipe(
      map(paramMap => {console.log('1'); return paramMap.get('id');}),
      flatMap(id => {console.log('2'); return this.curriculumService.getById(+id)}),
      take(1)
    );
    const subjects$ = this.subjectInfoService.getAllEnriched();

    forkJoin({curriculum: curriculum$, subjects: subjects$})
      .subscribe(result => {
        this.curriculum = result.curriculum;
        this.subjects = result.subjects;
        console.log('Here');
      });
  }

  subjectSelected(subject: RichSubjectInfo):void {
    console.log("subject selected: " + subject.denotation);
  }
}
