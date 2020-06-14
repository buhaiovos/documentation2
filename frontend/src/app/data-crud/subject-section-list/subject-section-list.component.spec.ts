import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SubjectSectionListComponent } from './subject-section-list.component';

describe('SubjectSectionComponent', () => {
  let component: SubjectSectionListComponent;
  let fixture: ComponentFixture<SubjectSectionListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SubjectSectionListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SubjectSectionListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
