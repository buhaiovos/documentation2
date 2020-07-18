import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { StateCertification } from "../../models/state-certification.model";
import { AbstractCrudService } from "../abstract/abstract-crud.service";

@Injectable()
export class StateCertificationService extends AbstractCrudService<StateCertification> {
  baseUrl: () => string = () => 'http://localhost:8080/v2/state-certifications';

  constructor(http: HttpClient) {
    super(http);
  }
}
