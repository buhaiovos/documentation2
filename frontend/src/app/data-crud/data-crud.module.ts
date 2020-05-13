import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SubjectListComponent } from "./subject-list/subject-list.component";
import { SubjectHeaderComponent } from "./subject-header/subject-header.component";
import { SubjectInfoComponent } from "./subject-info/subject-info.component";
import { ControlComponent } from "./control/control.component";
import { SubjectInfoService } from "./subject-info/subject-info.service";
import { FormsModule } from "@angular/forms";


@NgModule({
  providers: [
    SubjectInfoService
  ],
  declarations: [
    SubjectListComponent,
    SubjectHeaderComponent,
    SubjectInfoComponent,
    ControlComponent
  ],
  imports: [
    CommonModule,
    FormsModule
  ]
})
export class DataCrudModule {
}
