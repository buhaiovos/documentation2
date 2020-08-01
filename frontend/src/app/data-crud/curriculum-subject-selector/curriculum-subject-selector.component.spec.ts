import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CurriculumSubjectSelectorComponent } from './curriculum-subject-selector.component';

describe('CurriculumSubjectSelectorComponent', () => {
  let component: CurriculumSubjectSelectorComponent;
  let fixture: ComponentFixture<CurriculumSubjectSelectorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CurriculumSubjectSelectorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CurriculumSubjectSelectorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
