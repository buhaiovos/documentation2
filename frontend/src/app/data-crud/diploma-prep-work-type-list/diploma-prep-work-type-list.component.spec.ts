import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DiplomaPrepWorkTypeListComponent } from './diploma-prep-work-type-list.component';

describe('DiplomaPrepWorkTypeListComponent', () => {
  let component: DiplomaPrepWorkTypeListComponent;
  let fixture: ComponentFixture<DiplomaPrepWorkTypeListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DiplomaPrepWorkTypeListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DiplomaPrepWorkTypeListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
