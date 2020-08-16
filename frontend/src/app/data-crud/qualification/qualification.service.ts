import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Qualification } from "../../models/qualification.model";
import { AbstractCrudService } from "../abstract/abstract-crud.service";

@Injectable()
export class QualificationService extends AbstractCrudService<Qualification> {
  protected baseUrl: () => string = () => '/v2/qualifications';

  constructor(http: HttpClient) {
    super(http);
  }
}
