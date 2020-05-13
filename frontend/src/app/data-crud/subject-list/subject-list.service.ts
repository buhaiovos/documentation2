import { Injectable } from '@angular/core';
import { Subject } from "../../models/subject-model";
import { forkJoin, Observable } from "rxjs";
import { SubjectInfo } from "../../models/subject-info.model";
import { SubjectHeader } from "../../models/subject-header.model";
import { HttpClient } from "@angular/common/http";
import { map, take } from "rxjs/operators";
import { SubjectInfoService } from "../subject-info/subject-info.service";

@Injectable()
export class SubjectListService {
  private subjectHeadersUrl = 'http://localhost:8080/v2/subject-headers'
  private subjectInfosUrl = 'http://localhost:8080/v2/subject-infos'

  constructor(private http: HttpClient,
              private infoService: SubjectInfoService) {
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
    return this.http.get<SubjectInfo[]>(this.subjectInfosUrl).pipe(take(1));
  }

  private fetchSubjectHeaders(): Observable<SubjectHeader[]> {
    return this.http.get<SubjectHeader[]>(this.subjectHeadersUrl).pipe(take(1));
  }

  private toSubjects(headers: SubjectHeader[], infos: SubjectInfo[]): Subject[] {
    let headerAndInfosByHeaderId = {}
    headers.map(header => headerAndInfosByHeaderId[header.id] = new Subject(header, [], false));
    infos.map(info => headerAndInfosByHeaderId[info.subjectHeaderId]?.infos.push(info));
    return Object.values(headerAndInfosByHeaderId);
  }

  deleteInfo(infoId: number): Observable<any> {
    console.log("hey")
    return this.infoService.deleteById(infoId).pipe(take(1));
  }
}
