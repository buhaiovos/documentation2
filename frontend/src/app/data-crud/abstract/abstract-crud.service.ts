import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { DropdownOption } from "../../models/dropdown-option.model";

interface Entity {
  id: number;
}

export abstract class AbstractCrudService<T extends Entity> {
  abstract baseUrl(): string;

  protected byIdUrl: (id: number) => string = (id) => `${this.baseUrl()}/${id}`;
  protected enumerated: () => string = () => `${this.baseUrl()}/enumerated`;

  protected constructor(protected http: HttpClient) {
  }

  getAll(): Observable<T[]> {
    return this.http.get<T[]>(this.baseUrl());
  }

  getById(id: number): Observable<T> {
    return this.http.get<T>(this.byIdUrl(id));
  }

  save(entity: T): Observable<T> {
    return entity.id
      ? this.http.put<T>(this.byIdUrl(entity.id), entity)
      : this.http.post<T>(this.baseUrl(), entity);
  }

  deleteById(id: number): Observable<void> {
    return this.http.delete<any>(this.byIdUrl(id));
  }

  getOptions(): Observable<DropdownOption[]> {
    return this.http.get<DropdownOption[]>(this.enumerated());
  }
}
