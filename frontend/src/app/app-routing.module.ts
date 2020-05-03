import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {SubjectLoadTableComponent} from './components/subject-load-table/subject-load-table.component';
import {OtherLoadTableComponent} from './components/other-load-table/other-load-table.component';
import {StatisticsComponent} from './components/statistics/statistics.component';
import {SubjectListComponent} from "./components/lists/subjects/subject-list/subject-list.component";
import {SubjectHeaderComponent} from "./components/lists/subjects/subject-header/subject-header.component";
import {SubjectInfoComponent} from "./components/lists/subjects/subject-info/subject-info.component";


const routes: Routes = [
  {path: '', redirectTo: 'login', pathMatch: 'full'},
  {path: 'subjects', component: SubjectListComponent},
  {path: 'subject-load', component: SubjectLoadTableComponent},
  {path: 'other-load', component: OtherLoadTableComponent},
  {path: 'statistics', component: StatisticsComponent},
  {path: 'subject-headers', component: SubjectHeaderComponent},
  {path: 'subject-infos', component: SubjectInfoComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
