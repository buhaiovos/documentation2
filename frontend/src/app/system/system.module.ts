import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from "@angular/forms";
import { YearSwitcherComponent } from './year-switcher/year-switcher.component';


@NgModule({
  declarations: [YearSwitcherComponent],
  exports: [
    YearSwitcherComponent
  ],
  imports: [
    CommonModule,
    FormsModule
  ]
})
export class SystemModule { }
