import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Control } from "../../models/control.model";
import { Observable } from "rxjs";
import { DropdownOption } from "../../models/dropdown-option.model";
import { AbstractCrudService } from "../abstract/abstract-crud.service";

@Injectable()
export class ControlService extends AbstractCrudService<Control> {
  protected baseUrl(): string {
    return '/v2/controls';
  }

  static controlTypes = '/v2/controls/types';

  constructor(http: HttpClient) {
    super(http);
  }

  getTypes(): Observable<DropdownOption[]> {
    return this.http.get<DropdownOption[]>(ControlService.controlTypes);
  }
}
