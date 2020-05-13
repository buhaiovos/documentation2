import { TestBed } from '@angular/core/testing';

import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { SubjectListService } from './subject-list.service';
import { HttpClient } from "@angular/common/http";
import { SubjectHeader } from "../../models/subject-header.model";
import { SubjectInfo } from "../../models/subject-info.model";
import { Subject } from "../../models/subject-model";
import { DropdownOption } from "../../models/dropdown-option.model";
import { DataCrudModule } from "../data-crud.module";

describe('SubjectListService', () => {
  const SUBJECT_HEADERS_URL = 'http://localhost:8080/v2/subject-headers';
  const SUBJECT_INFOS_URS = 'http://localhost:8080/v2/subject-infos';

  let service: SubjectListService;
  let httpClient: HttpClient;
  let httpTestingController: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, DataCrudModule],
      providers: [SubjectListService, HttpClient]
    });
    service = TestBed.inject(SubjectListService);
    httpClient = TestBed.inject(HttpClient);
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  describe('fetchSubjectList', () => {
    it('should fetch subject headers and infos', (done) => {
      const EMPTY_ARRAY = [];
      service.fetchSubjectList()
        .subscribe(subjects => {
          expect(subjects).toEqual(EMPTY_ARRAY);
          done();
        });

      const infosRequest = httpTestingController.expectOne(SUBJECT_INFOS_URS);
      const headersRequest = httpTestingController.expectOne(SUBJECT_HEADERS_URL);

      expect(headersRequest.request.method).toEqual('GET');
      expect(infosRequest.request.method).toEqual('GET');

      infosRequest.flush(EMPTY_ARRAY);
      headersRequest.flush(EMPTY_ARRAY);
    });

    it('should combine headers and infos into subjects', (done) => {
      const matanId = 1;
      const teorverId = 2;
      const matan = new SubjectHeader(
        matanId,
        'Matan',
        {id: 1, text: '42'} as DropdownOption,
        {id: 1, text: '42'} as DropdownOption,
        {id: 1, text: '42'} as DropdownOption,
        {id: 1, text: '42'} as DropdownOption
      );
      const teorver = new SubjectHeader(
        teorverId,
        'Teorver',
        null,
        {id: 1, text: '42'} as DropdownOption,
        {id: 1, text: '42'} as DropdownOption,
        {id: 1, text: '42'} as DropdownOption
      );
      const headers = [matan, teorver];

      const matan1 = new SubjectInfo(
        matanId, 1, 1, 1, 0,
        0, 0, 0, 0,
        0, 0, []
      );
      const teorver1 = new SubjectInfo(
        teorverId, 3, 1, 0,
        0, 0, 0, 0,
        0, 0, 0, []
      )
      const matan2 = new SubjectInfo(
        matanId, 2, 1, 0,
        0, 0, 0, 0,
        0, 0, 0, []
      );
      const teorver2 = new SubjectInfo(
        teorverId, 4, 1, 0,
        0, 0, 0, 0,
        0, 0, 0, []
      );
      const noHeader = new SubjectInfo(
        null, 1, 1, 0,
        0, 0, 0, 0,
        0, 0, 42, []
      );
      const infos = [matan1, matan2, teorver1, teorver2, noHeader];

      service.fetchSubjectList()
        .subscribe(subjects => {
          expect(subjects).toEqual([
            new Subject(matan, [matan1, matan2], false),
            new Subject(teorver, [teorver1, teorver2], false)
          ]);
          done();
        });

      httpTestingController.expectOne(SUBJECT_INFOS_URS).flush(infos);
      httpTestingController.expectOne(SUBJECT_HEADERS_URL).flush(headers);

    });
  });
});
