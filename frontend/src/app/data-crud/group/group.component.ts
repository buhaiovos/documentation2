import { Component, OnInit } from '@angular/core';
import { GroupService } from "./group.service";
import { Group } from "../../models/group.model";
import { ActivatedRoute, Router } from "@angular/router";
import { DropdownOption } from "../../models/dropdown-option.model";
import { SpecializationService } from "../specialization/specialization.service";
import { QualificationService } from "../qualification/qualification.service";
import { EducationFormService } from "../education-form/education-form.service";
import { Utils } from "../../util/utils";
import { forkJoin, Observable } from "rxjs";
import { flatMap } from "rxjs/operators";

@Component({
  selector: 'app-group',
  templateUrl: './group.component.html',
  styleUrls: ['./group.component.css'],
  providers: [
    GroupService,
    SpecializationService,
    QualificationService,
    EducationFormService
  ]
})
export class GroupComponent implements OnInit {

  group: Group;
  specializations: DropdownOption[];
  qualifications: DropdownOption[];
  educationForms: DropdownOption[];


  constructor(private router: Router,
              private activeRoute: ActivatedRoute,
              private service: GroupService,
              private specializationService: SpecializationService,
              private qualificationService: QualificationService,
              private educationFormService: EducationFormService) {
  }

  ngOnInit(): void {
    const group$ = Utils.takeOne$(this.getGroup());
    const specializations$ = Utils.takeOne$(this.specializationService.getOptions());
    const qualifications$ = Utils.takeOne$(this.qualificationService.getOptions());
    const educationForms$ = Utils.takeOne$(this.educationFormService.getOptions());

    forkJoin({
      group: group$,
      specializations: specializations$,
      qualifications: qualifications$,
      educationForms: educationForms$
    }).subscribe(
      aggregate => {
        this.group = aggregate.group;
        this.specializations = aggregate.specializations;
        this.qualifications = aggregate.qualifications;
        this.educationForms = aggregate.educationForms;
      }
    );
  }

  save(): void {
    Utils.takeOne$(this.service.save(this.group))
      .subscribe((_) => this.router.navigate(['/groups']));
  }

  private getGroup(): Observable<Group> {
    const fetchFunction: (id: number) => Observable<Group> = (id) => this.service.getById(id);
    const createFunction: () => Group = () => Group.empty();
    return this.activeRoute.paramMap.pipe(
      flatMap(map => Utils.fetchOrCreate$(fetchFunction, createFunction, map.get('id')))
    );
  }
}
