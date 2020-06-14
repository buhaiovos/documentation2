import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SubjectSectionComponent } from './subject-section.component';

describe('SubjectSectionComponent', () => {
  let component: SubjectSectionComponent;
  let fixture: ComponentFixture<SubjectSectionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SubjectSectionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SubjectSectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
