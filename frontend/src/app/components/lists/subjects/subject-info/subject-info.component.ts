import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, ParamMap, Router} from "@angular/router";
import {SubjectInfoService} from "./subject-info.service";
import {SubjectInfo} from "../../../../models/subject-info.model";
import {flatMap, take} from "rxjs/operators";
import {Observable, of} from "rxjs";

@Component({
  selector: 'app-subject-info',
  templateUrl: './subject-info.component.html',
  styleUrls: ['./subject-info.component.css'],
  providers: [SubjectInfoService]
})
export class SubjectInfoComponent implements OnInit {

  subjectInfo: SubjectInfo;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private service: SubjectInfoService) {
  }

  ngOnInit(): void {
    this.route.paramMap
      .pipe(flatMap(params => this.getSubjectInfo(params)))
      .pipe(take(1))
      .subscribe(info => this.subjectInfo = info);
  }

  private getSubjectInfo(params: ParamMap): Observable<SubjectInfo> {
    const id = params.get('id');
    const headerId = params.get('headerId');
    return id ? this.service.getById(+id) : of(new SubjectInfo(+headerId));
  }

  private save(): void {
    this.service.save(this.subjectInfo)
      .pipe(take(1))
      .subscribe(() => this.router.navigate(['subjects']))
  }
}
