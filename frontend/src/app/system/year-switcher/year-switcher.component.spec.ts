import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { YearSwitcherComponent } from './year-switcher.component';

describe('YearSwitcherComponent', () => {
  let component: YearSwitcherComponent;
  let fixture: ComponentFixture<YearSwitcherComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ YearSwitcherComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(YearSwitcherComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
