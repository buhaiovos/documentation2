import { Injectable } from "@angular/core";
import { SubjectInfo } from "../../models/subject-info.model";
import { HttpClient } from "@angular/common/http";
import { AbstractCrudService } from "../abstract/abstract-crud.service";

@Injectable()
export class SubjectInfoService extends AbstractCrudService<SubjectInfo> {
  protected baseUrl: () => string = () => 'http://localhost:8080/v2/subject-infos';

  constructor(http: HttpClient) {
    super(http);
  }
}
