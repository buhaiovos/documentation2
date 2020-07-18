import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { SubjectSection } from "../../models/subject-section.model";
import { AbstractCrudService } from "../abstract/abstract-crud.service";

@Injectable()
export class SubjectSectionService extends AbstractCrudService<SubjectSection> {
  baseUrl: () => string = () => 'http://localhost:8080/v2/sections';

  constructor(http: HttpClient) {
    super(http);
  }
}
