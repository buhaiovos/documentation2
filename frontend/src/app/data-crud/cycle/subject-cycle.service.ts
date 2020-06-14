import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { DropdownOption } from "../../models/dropdown-option.model";
import { Observable } from "rxjs";

@Injectable()
export class SubjectCycleService {
  private baseUrl = 'http://localhost:8080/v2/cycles'
  private dropdownOptionsUrl = `${this.baseUrl}/enumerated`;

  constructor(private http: HttpClient) {
  }

  getOptions(): Observable<DropdownOption[]> {
    return this.http.get<DropdownOption[]>(this.dropdownOptionsUrl);
  }
}
