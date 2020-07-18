import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { DropdownOption } from "../../models/dropdown-option.model";
import { DiplomaPrepWorkType } from "../../models/diploma-prep-work-type.model";

@Injectable()
export class DiplomaPrepWorkTypeService {
  private baseUrl: string = 'http://localhost:8080/v2/work-types';
  private byIdUrl: (id: number) => string = id => `${this.baseUrl}/${id}`;
  private options: string = `${this.baseUrl}/enumerated`

  constructor(private http: HttpClient) {
  }

  getAll(): Observable<DiplomaPrepWorkType[]> {
    return this.http.get<DiplomaPrepWorkType[]>(this.baseUrl);
  }

  getById(id: number): Observable<DiplomaPrepWorkType> {
    return this.http.get<DiplomaPrepWorkType>(this.byIdUrl(id));
  }

  save(department: DiplomaPrepWorkType): Observable<DiplomaPrepWorkType> {
    return department.id
      ? this.http.put<DiplomaPrepWorkType>(this.byIdUrl(department.id), department)
      : this.http.post<DiplomaPrepWorkType>(this.baseUrl, department);
  }

  deleteById(id: number): Observable<void> {
    return this.http.delete<any>(this.byIdUrl(id));
  }

  getOptions(): Observable<DropdownOption[]> {
    return this.http.get<DropdownOption[]>(this.options);
  }
}
