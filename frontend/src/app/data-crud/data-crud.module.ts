import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SubjectListComponent } from "./subject-list/subject-list.component";
import { SubjectHeaderComponent } from "./subject-header/subject-header.component";
import { SubjectInfoComponent } from "./subject-info/subject-info.component";
import { ControlComponent } from "./control/control.component";
import { SubjectInfoService } from "./subject-info/subject-info.service";
import { FormsModule } from "@angular/forms";
import { SubjectSectionListComponent } from "./subject-section-list/subject-section-list.component";
import { SubjectSectionComponent } from './subject-section/subject-section.component';
import { DepartmentListComponent } from './department-list/department-list.component';
import { DepartmentComponent } from './department/department.component';
import { SpecializationListComponent } from './specialization-list/specialization-list.component';
import { SpecializationComponent } from './specialization/specialization.component';
import { QualificationComponent } from './qualification/qualification.component';
import { QualificationListComponent } from './qualification-list/qualification-list.component';
import { StateCertificationComponent } from './state-certification/state-certification.component';
import { StateCertificationListComponent } from './state-certification-list/state-certification-list.component';
import { DiplomaPrepWorkTypeComponent } from './diploma-prep-work-type/diploma-prep-work-type.component';
import { DiplomaPrepWorkTypeListComponent } from './diploma-prep-work-type-list/diploma-prep-work-type-list.component';
import { GroupsListComponent } from './groups-list/groups-list.component';
import { GroupComponent } from './group/group.component';
import { CurriculumListComponent } from './curriculum-list/curriculum-list.component';
import { CurriculumComponent } from './curriculum/curriculum.component';


@NgModule({
  providers: [
    SubjectInfoService
  ],
  declarations: [
    SubjectListComponent,
    SubjectHeaderComponent,
    SubjectInfoComponent,
    ControlComponent,
    SubjectSectionListComponent,
    SubjectSectionComponent,
    DepartmentListComponent,
    DepartmentComponent,
    SpecializationListComponent,
    SpecializationComponent,
    SpecializationComponent,
    QualificationComponent,
    QualificationListComponent,
    StateCertificationComponent,
    StateCertificationListComponent,
    DiplomaPrepWorkTypeComponent,
    DiplomaPrepWorkTypeListComponent,
    GroupsListComponent,
    GroupComponent,
    CurriculumListComponent,
    CurriculumComponent
  ],
  imports: [
    CommonModule,
    FormsModule
  ]
})
export class DataCrudModule {
}
