import { AbstractCrudService } from "../abstract/abstract-crud.service";
import { Group } from "../../models/group.model";
import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";

@Injectable()
export class GroupService extends AbstractCrudService<Group> {
  protected baseUrl: () => string = () => 'http://localhost:8080/v2/academic-groups'

  constructor(http: HttpClient) {
    super(http);
  }


}
