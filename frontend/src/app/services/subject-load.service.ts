import { Injectable } from '@angular/core';
import { Load } from '../models/load.model';
import { Observable } from 'rxjs';
import { HttpClient, HttpParams } from '@angular/common/http';

@Injectable()
export class SubjectLoadService {
  private subjectLoadApiEndpoint = '/subject-load';
  private distributedSubjectLoadApiEndpoint = '/distributed-subject-load';

  constructor(private http: HttpClient) {
  }

  fetchSubjectLoad(semester: string, educationForm: string, financing: string): Observable<Load[]> {
    return this.http.get<Load[]>(
      this.subjectLoadApiEndpoint,
      {
        params: new HttpParams()
          .append('semester', `${semester}`)
          .append('education_form', educationForm)
          .append('financing', financing)
      }
    );
  }

  submit(loadId: number, type: string, value: number, staffId: number): Observable<any> {
    console.log(`submitting for load '${loadId}' of type '${type}' amount of '${value}' to an employee with id '${staffId}'`);
    return this.http.post(
      this.distributedSubjectLoadApiEndpoint,
      {
        load_id: `${loadId}`,
        type: `${type}`,
        value: `${value}`,
        staff_id: `${staffId}`
      }
    );
  }
}
