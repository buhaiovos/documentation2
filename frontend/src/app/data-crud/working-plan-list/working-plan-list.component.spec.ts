import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WorkingPlanListComponent } from './working-plan-list.component';

describe('WorkingPlanListComponent', () => {
  let component: WorkingPlanListComponent;
  let fixture: ComponentFixture<WorkingPlanListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WorkingPlanListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WorkingPlanListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
