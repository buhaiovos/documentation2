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
import { SpecializationListComponent } from "./data-crud/specialization-list/specialization-list.component";
import { SpecializationComponent } from "./data-crud/specialization/specialization.component";
import { QualificationListComponent } from "./data-crud/qualification-list/qualification-list.component";
import { QualificationComponent } from "./data-crud/qualification/qualification.component";
import { StateCertificationListComponent } from "./data-crud/state-certification-list/state-certification-list.component";
import { StateCertificationComponent } from "./data-crud/state-certification/state-certification.component";
import { DiplomaPrepWorkTypeComponent } from "./data-crud/diploma-prep-work-type/diploma-prep-work-type.component";
import { DiplomaPrepWorkTypeListComponent } from "./data-crud/diploma-prep-work-type-list/diploma-prep-work-type-list.component";
import { GroupsListComponent } from "./data-crud/groups-list/groups-list.component";
import { GroupComponent } from "./data-crud/group/group.component";
import { CurriculumListComponent } from "./data-crud/curriculum-list/curriculum-list.component";
import { CurriculumSubjectSelectorComponent } from "./data-crud/curriculum-subject-selector/curriculum-subject-selector.component";


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
  {path: 'department', component: DepartmentComponent},
  {path: 'specializations', component: SpecializationListComponent},
  {path: 'specialization', component: SpecializationComponent},
  {path: 'qualifications', component: QualificationListComponent},
  {path: 'qualification', component: QualificationComponent},
  {path: 'certifications', component: StateCertificationListComponent},
  {path: 'certification', component: StateCertificationComponent},
  {path: 'diploma-preparation/work-type', component: DiplomaPrepWorkTypeComponent},
  {path: 'diploma-preparation/work-types', component: DiplomaPrepWorkTypeListComponent},
  {path: 'groups', component: GroupsListComponent},
  {path: 'group', component: GroupComponent},
  {path: 'curricula', component: CurriculumListComponent},
  {path: 'curriculum-subject-list-selection', component: CurriculumSubjectSelectorComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
