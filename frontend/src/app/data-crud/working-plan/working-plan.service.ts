import { AbstractCrudService } from "../abstract/abstract-crud.service";
import { WorkingPlan } from "../../models/working-plan.model";
import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";

@Injectable()
export class WorkingPlanService extends AbstractCrudService<WorkingPlan> {
  protected baseUrl: () => string = () => '/v2/working-plans';

  constructor(http: HttpClient) {
    super(http);
  }
}
