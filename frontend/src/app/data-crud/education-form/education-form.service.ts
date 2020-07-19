import { Injectable } from "@angular/core";
import { AbstractCrudService } from "../abstract/abstract-crud.service";
import { EducationForm } from "../../models/education-form.model";
import { HttpClient } from "@angular/common/http";

@Injectable()
export class EducationFormService extends AbstractCrudService<EducationForm> {
  protected baseUrl: () => string = () => 'http://localhost:8080/v2/education-forms'

  constructor(http: HttpClient) {
    super(http);
  }
}
