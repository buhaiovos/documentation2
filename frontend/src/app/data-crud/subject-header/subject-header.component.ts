import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from "@angular/router";
import { flatMap, take } from "rxjs/operators";
import { SubjectHeaderService } from "./subject-header.service";
import { SubjectHeader } from "../../models/subject-header.model";
import { Observable, of } from "rxjs";

@Component({
  selector: 'app-subject-header',
  templateUrl: './subject-header.component.html',
  styleUrls: ['./subject-header.component.css'],
  providers: [SubjectHeaderService]
})
export class SubjectHeaderComponent implements OnInit {
  subjectHeader: SubjectHeader;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private subjectHeaderService: SubjectHeaderService) {
  }

  ngOnInit(): void {
    this.route.paramMap
      .pipe(flatMap(params => this.getSubjectHeader(params.get('id'))))
      .pipe(take(1))
      .subscribe(header => this.subjectHeader = header);
  }

  private getSubjectHeader(id: string): Observable<SubjectHeader> {
    return (id)
      ? this.subjectHeaderService.getById(+id)
      : of(new SubjectHeader(undefined, 'New'))
  }

  save(): void {
    this.subjectHeaderService.save(this.subjectHeader)
      .pipe(take(1))
      .subscribe(() => this.router.navigate(['subjects']));
  }
}
