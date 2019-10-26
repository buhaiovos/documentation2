import {inject, TestBed} from '@angular/core/testing';

import {LoadDistributorComponentService} from './load-distributor-component.service';

describe('LoadDistributorComponentService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [LoadDistributorComponentService]
    });
  });

  it('should be created', inject([LoadDistributorComponentService], (service: LoadDistributorComponentService) => {
    expect(service).toBeTruthy();
  }));
});
