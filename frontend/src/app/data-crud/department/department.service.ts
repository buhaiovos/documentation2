import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Department } from "../../models/department.model";
import { AbstractCrudService } from "../abstract/abstract-crud.service";

@Injectable()
export class DepartmentService extends AbstractCrudService<Department> {
  protected baseUrl: () => string = () => '/v2/departments';

  constructor(http: HttpClient) {
    super(http);
  }
}
