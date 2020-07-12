import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { StateCertificationListComponent } from './state-certification-list.component';

describe('StateCertificationListComponent', () => {
  let component: StateCertificationListComponent;
  let fixture: ComponentFixture<StateCertificationListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ StateCertificationListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StateCertificationListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
