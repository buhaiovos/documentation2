import { AbstractCrudService } from "../abstract/abstract-crud.service";
import { DiplomaPreparation } from "../../models/diploma-prep.model";
import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";

@Injectable()
export class DiplomaPreparationService extends AbstractCrudService<DiplomaPreparation> {
  protected baseUrl: () => string = () => "/v2/diploma-preparations"

  constructor(http: HttpClient) {
    super(http);
  }
}
