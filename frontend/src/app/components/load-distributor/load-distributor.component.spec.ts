import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {LoadDistributorComponent} from './load-distributor.component';

describe('LoadDistributorComponent', () => {
  let component: LoadDistributorComponent;
  let fixture: ComponentFixture<LoadDistributorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [LoadDistributorComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoadDistributorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
