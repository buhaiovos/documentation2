import {inject, TestBed} from '@angular/core/testing';

import {SubjectLoadComponentService} from './subject-load-component.service';

describe('SubjectLoadComponentService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [SubjectLoadComponentService]
    });
  });

  it('should be created', inject([SubjectLoadComponentService], (service: SubjectLoadComponentService) => {
    expect(service).toBeTruthy();
  }));
});
