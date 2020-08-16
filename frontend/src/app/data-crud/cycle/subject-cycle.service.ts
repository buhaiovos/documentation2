import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { SubjectCycle } from "../../models/subject-cycle.model";
import { AbstractCrudService } from "../abstract/abstract-crud.service";

@Injectable()
export class SubjectCycleService extends AbstractCrudService<SubjectCycle> {
  protected baseUrl: () => string = () => '/v2/cycles'

  constructor(http: HttpClient) {
    super(http);
  }
}
