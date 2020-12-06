import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DiplomaPreparationComponent } from './diploma-preparation.component';

describe('DiplomaPreparationComponent', () => {
  let component: DiplomaPreparationComponent;
  let fixture: ComponentFixture<DiplomaPreparationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DiplomaPreparationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DiplomaPreparationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
