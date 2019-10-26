import {Injectable} from '@angular/core';
import {Load} from '../models/load.model';
import {Observable} from 'rxjs';
import {HttpClient, HttpParams} from '@angular/common/http';

@Injectable()
export class OtherLoadService {
  private allOtherLoadUrl = 'http://localhost:8080/other-load';
  private submitOtherLoadUrl = 'http://localhost:8080/distributed-other-load';

  constructor(private http: HttpClient) {
  }

  fetchOtherLoad(semester: number,
                 educationForm: string,
                 financing: string
  ): Observable<Load[]> {
    return this.http.get<Load[]>(
      this.allOtherLoadUrl,
      {
        params: new HttpParams()
          .append('semester', `${semester}`)
          .append('education_form', educationForm)
          .append('financing', financing)
      }
    );
  }

  submitOtherLoad(loadId: number, staffId: number, value: number): Observable<any> {
    return this.http.post(
      this.submitOtherLoadUrl,
      {
        load_id: `${loadId}`,
        staff_id: `${staffId}`,
        value: `${value}`
      }
    );
  }
}
