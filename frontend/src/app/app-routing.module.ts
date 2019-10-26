import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {SubjectLoadTableComponent} from './components/subject-load-table/subject-load-table.component';
import {OtherLoadTableComponent} from './components/other-load-table/other-load-table.component';
import {StatisticsComponent} from './components/statistics/statistics.component';


const routes: Routes = [
  {path: '', redirectTo: 'subject-load', pathMatch: 'full'},
  {path: 'subject-load', component: SubjectLoadTableComponent},
  {path: 'other-load', component: OtherLoadTableComponent},
  {path: 'statistics', component: StatisticsComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
