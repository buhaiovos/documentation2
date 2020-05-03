import {Injectable} from "@angular/core";
import {SubjectInfo} from "../../../../models/subject-info.model";
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";

@Injectable()
export class SubjectInfoService {

  private infoByIdUrl = id => `http://localhost:8080/v2/subject-infos/${id}`;
  private infosUrl = 'http://localhost:8080/v2/subject-infos';

  constructor(private http: HttpClient) {
  }

  getById(id: number): Observable<SubjectInfo> {
    return this.http.get<SubjectInfo>(this.infoByIdUrl(id));
  }

  save(info: SubjectInfo): Observable<SubjectInfo> {
    return info.id
      ? this.http.put<SubjectInfo>(this.infoByIdUrl(info.id), info)
      : this.http.post<SubjectInfo>(this.infosUrl, info);
  }
}
