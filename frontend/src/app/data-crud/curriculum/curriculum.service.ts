import { Injectable } from "@angular/core";
import { AbstractCrudService } from "../abstract/abstract-crud.service";
import { Curriculum } from "../../models/curriculum.model";
import { HttpClient } from "@angular/common/http";

@Injectable()
export class CurriculumService extends AbstractCrudService<Curriculum> {
  protected baseUrl: () => string = () => 'http://localhost:8080/v2/curricula';

  constructor(http: HttpClient) {
    super(http);
  }
}
