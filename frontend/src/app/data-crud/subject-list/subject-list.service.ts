import { Injectable } from '@angular/core';
import { Subject } from "../../models/subject-model";
import { forkJoin, Observable } from "rxjs";
import { SubjectInfo } from "../../models/subject-info.model";
import { SubjectHeader } from "../../models/subject-header.model";
import { map, take } from "rxjs/operators";
import { SubjectInfoService } from "../subject-info/subject-info.service";
import { SubjectHeaderService } from "../subject-header/subject-header.service";

@Injectable()
export class SubjectListService {
  constructor(private infoService: SubjectInfoService,
              private headerService: SubjectHeaderService) {
  }

  fetchSubjectList(): Observable<Subject[]> {
    return forkJoin({
      headers: this.fetchSubjectHeaders(),
      infos: this.fetchSubjectInfos()
    }).pipe(
      map(combined => this.toSubjects(combined.headers, combined.infos))
    );
  }

  private fetchSubjectInfos(): Observable<SubjectInfo[]> {
    return this.infoService.getAll().pipe(take(1));
  }

  private fetchSubjectHeaders(): Observable<SubjectHeader[]> {
    return this.headerService.getAll().pipe(take(1));
  }

  private toSubjects(headers: SubjectHeader[], infos: SubjectInfo[]): Subject[] {
    let headerAndInfosByHeaderId = {}
    headers.map(header => headerAndInfosByHeaderId[header.id] = new Subject(header, [], false));
    infos.map(info => headerAndInfosByHeaderId[info.subjectHeaderId]?.infos.push(info));
    return Object.values(headerAndInfosByHeaderId);
  }

  deleteInfo(infoId: number): Observable<any> {
    return this.infoService.deleteById(infoId);
  }

  deleteHeader(header: SubjectHeader): Observable<any> {
    return this.headerService.deleteById(header.id);
  }
}
