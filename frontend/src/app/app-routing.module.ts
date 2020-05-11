import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SubjectLoadTableComponent } from './components/subject-load-table/subject-load-table.component';
import { OtherLoadTableComponent } from './components/other-load-table/other-load-table.component';
import { StatisticsComponent } from './components/statistics/statistics.component';
import { SubjectListComponent } from "./data-crud/subject-list/subject-list.component";
import { SubjectHeaderComponent } from "./data-crud/subject-header/subject-header.component";
import { SubjectInfoComponent } from "./data-crud/subject-info/subject-info.component";
import { ControlComponent } from "./data-crud/control/control.component";


const routes: Routes = [
  {path: '', redirectTo: 'login', pathMatch: 'full'},
  {path: 'subjects', component: SubjectListComponent},
  {path: 'subject-load', component: SubjectLoadTableComponent},
  {path: 'other-load', component: OtherLoadTableComponent},
  {path: 'statistics', component: StatisticsComponent},
  {path: 'subject-headers', component: SubjectHeaderComponent},
  {path: 'subject-infos', component: SubjectInfoComponent},
  {path: 'controls', component: ControlComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
