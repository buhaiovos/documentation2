import { Injectable } from "@angular/core";
import { SubjectInfo } from "../../models/subject-info.model";
import { HttpClient } from "@angular/common/http";
import { AbstractCrudService } from "../abstract/abstract-crud.service";
import { RichSubjectInfo } from "../../models/rich-subject-info.model";
import { Observable } from "rxjs";

@Injectable()
export class SubjectInfoService extends AbstractCrudService<SubjectInfo> {
  protected baseUrl: () => string = () => 'http://localhost:8080/v2/subject-infos';
  private enriched: string = `${this.baseUrl()}/enriched`;

  constructor(http: HttpClient) {
    super(http);
  }

  getAllEnriched(): Observable<RichSubjectInfo[]> {
    return this.http.get<RichSubjectInfo[]>(this.enriched);
  }
}
