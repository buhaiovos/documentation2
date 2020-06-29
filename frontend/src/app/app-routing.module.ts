import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SubjectLoadTableComponent } from './components/subject-load-table/subject-load-table.component';
import { OtherLoadTableComponent } from './components/other-load-table/other-load-table.component';
import { StatisticsComponent } from './components/statistics/statistics.component';
import { SubjectListComponent } from "./data-crud/subject-list/subject-list.component";
import { SubjectHeaderComponent } from "./data-crud/subject-header/subject-header.component";
import { SubjectInfoComponent } from "./data-crud/subject-info/subject-info.component";
import { ControlComponent } from "./data-crud/control/control.component";
import { SubjectSectionListComponent } from "./data-crud/subject-section-list/subject-section-list.component";
import { SubjectSectionComponent } from "./data-crud/subject-section/subject-section.component";
import { DepartmentListComponent } from "./data-crud/department-list/department-list.component";
import { DepartmentComponent } from "./data-crud/department/department.component";


const routes: Routes = [
  {path: '', redirectTo: 'login', pathMatch: 'full'},
  {path: 'subjects', component: SubjectListComponent},
  {path: 'subject-load', component: SubjectLoadTableComponent},
  {path: 'other-load', component: OtherLoadTableComponent},
  {path: 'statistics', component: StatisticsComponent},
  {path: 'subject-headers', component: SubjectHeaderComponent},
  {path: 'subject-infos', component: SubjectInfoComponent},
  {path: 'controls', component: ControlComponent},
  {path: 'sections', component: SubjectSectionListComponent},
  {path: 'section', component: SubjectSectionComponent},
  {path: 'departments', component: DepartmentListComponent},
  {path: 'department', component: DepartmentComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
