import { AbstractCrudService } from "../abstract/abstract-crud.service";
import { WorkingPlan } from "../../models/working-plan.model";
import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { DropdownOption } from "../../models/dropdown-option.model";
import { Observable } from "rxjs";

@Injectable()
export class WorkingPlanService extends AbstractCrudService<WorkingPlan> {
  protected baseUrl: () => string = () => '/v2/working-plans';

  constructor(http: HttpClient) {
    super(http);
  }

  public addDiplomaPreparation(workingPlanId: number,
                               diplomaPreparation: DropdownOption): Observable<WorkingPlan> {
    return this.http.patch<WorkingPlan>(
      `${this.baseUrl()}/${workingPlanId}/diploma-preparations`,
      diplomaPreparation
    );
  }
}
