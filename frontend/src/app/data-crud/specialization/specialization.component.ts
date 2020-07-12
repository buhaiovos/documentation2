import { Component, OnInit } from '@angular/core';
import { SpecializationService } from "./specialization.service";
import { Specialization } from "../../models/specialization.model";
import { ActivatedRoute, Router } from "@angular/router";
import { forkJoin, Observable } from "rxjs";
import { flatMap } from "rxjs/operators";
import { Utils } from "../../util/utils";
import { DropdownOption } from "../../models/dropdown-option.model";
import { DepartmentService } from "../department/department.service";

@Component({
  selector: 'app-specialization',
  templateUrl: './specialization.component.html',
  styleUrls: ['./specialization.component.css'],
  providers: [SpecializationService, DepartmentService]
})
export class SpecializationComponent implements OnInit {

  specialization: Specialization;
  departments: DropdownOption[];

  constructor(private router: Router,
              private route: ActivatedRoute,
              private service: SpecializationService,
              private departmentService: DepartmentService) {
  }

  ngOnInit(): void {
    const specializations$: Observable<Specialization> = Utils.takeOne$(this.getSpecialization());
    const departments$: Observable<DropdownOption[]> = Utils.takeOne$(this.departmentService.getOptions());

    forkJoin({
      section: specializations$,
      cycles: departments$
    }).subscribe(
      result => {
        this.specialization = result.section;
        this.departments = result.cycles;
      }
    );
  }

  save() {
    Utils.takeOne$(
      this.service.save(this.specialization)
    ).subscribe(
      () => this.router.navigate(['/specializations'])
    )
  }

  private getSpecialization(): Observable<Specialization> {
    const fetchFunction: (id: number) => Observable<Specialization> = id => this.service.getById(id);
    const createFunction: () => Specialization = () => Specialization.empty();
    return this.route.paramMap
      .pipe(
        flatMap(params => Utils.fetchOrCreate$(fetchFunction, createFunction, params.get('id')))
      );
  }
}
