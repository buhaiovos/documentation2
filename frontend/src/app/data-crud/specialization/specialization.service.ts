import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Specialization } from "../../models/specialization.model";
import { AbstractCrudService } from "../abstract/abstract-crud.service";

@Injectable()
export class SpecializationService extends AbstractCrudService<Specialization> {
  baseUrl: () => string = () => 'http://localhost:8080/v2/specializations';

  public constructor(http: HttpClient) {
    super(http);
  }
}
