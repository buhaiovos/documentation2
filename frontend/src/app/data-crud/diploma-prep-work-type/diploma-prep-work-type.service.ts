import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { AbstractCrudService } from "../abstract/abstract-crud.service";
import { DiplomaPrepWorkType } from "../../models/diploma-prep-work-type.model";

@Injectable()
export class DiplomaPrepWorkTypeService extends AbstractCrudService<DiplomaPrepWorkType> {
  protected baseUrl: () => string = () => 'http://localhost:8080/v2/work-types';

  constructor(http: HttpClient) {
    super(http);
  }
}
