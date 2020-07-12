import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { StateCertificationComponent } from './state-certification.component';

describe('StateCertificationComponent', () => {
  let component: StateCertificationComponent;
  let fixture: ComponentFixture<StateCertificationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ StateCertificationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StateCertificationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
