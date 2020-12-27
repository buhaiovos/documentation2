import { Injectable } from '@angular/core';
import { AbstractCrudService } from "../abstract/abstract-crud.service";
import { Practice } from "../../models/practice.model";
import { HttpClient } from "@angular/common/http";

@Injectable()
export class PracticeService extends AbstractCrudService<Practice>{
  baseUrl = () => '/v2/practices'

  constructor(http: HttpClient) {
    super(http);
  }

}
