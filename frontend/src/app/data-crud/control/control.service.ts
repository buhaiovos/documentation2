import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Control } from "../../models/control.model";
import { Observable } from "rxjs";
import { DropdownOption } from "../../models/dropdown-option.model";

@Injectable()
export class ControlService {
  static controlById = (id: number) => `http://localhost:8080/v2/controls/${id}`;
  static controls = 'http://localhost:8080/v2/controls';
  static controlTypes = 'http://localhost:8080/v2/controls/types';

  constructor(private http: HttpClient) {
  }

  getById(id: number): Observable<Control> {
    return this.http.get<Control>(ControlService.controlById(id));
  }

  save(control: Control): Observable<Control> {
    return control.id
      ? this.http.put<Control>(ControlService.controlById(control.id), control)
      : this.http.post<Control>(ControlService.controls, control);
  }

  deleteById(id: number) {
    return this.http.delete<any>(ControlService.controlById(id));
  }

  getTypes(): Observable<DropdownOption[]> {
    return this.http.get<DropdownOption[]>(ControlService.controlTypes);
  }
}
