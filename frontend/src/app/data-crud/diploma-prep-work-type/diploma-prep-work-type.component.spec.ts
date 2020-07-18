import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DiplomaPrepWorkTypeComponent } from './diploma-prep-work-type.component';

describe('DiplomaPrepWorkTypeComponent', () => {
  let component: DiplomaPrepWorkTypeComponent;
  let fixture: ComponentFixture<DiplomaPrepWorkTypeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DiplomaPrepWorkTypeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DiplomaPrepWorkTypeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
