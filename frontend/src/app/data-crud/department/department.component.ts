import { Component, OnInit } from '@angular/core';
import { Department } from "../../models/department.model";
import { ActivatedRoute, Router } from "@angular/router";
import { DepartmentService } from "./department.service";
import { flatMap } from "rxjs/operators";
import { Utils } from "../../util/utils";
import { Observable } from "rxjs";

@Component({
  selector: 'app-department',
  templateUrl: './department.component.html',
  styleUrls: ['./department.component.css'],
  providers: [DepartmentService]
})
export class DepartmentComponent implements OnInit {
  department: Department;

  constructor(private router: Router,
              private route: ActivatedRoute,
              private service: DepartmentService) {
  }

  ngOnInit(): void {
    const fetchFunction: (id: number) => Observable<Department> = id => this.service.getById(id);
    const createFunction: () => Department = () => Department.empty();
    this.route.paramMap
      .pipe(
        flatMap(params => Utils.fetchOrCreate$(fetchFunction, createFunction, params.get('id')))
      )
      .subscribe(department => this.department = department);
  }

  save(): void {
    Utils.takeOne$(
      this.service.save(this.department)
    ).subscribe(
      () => this.router.navigate(['/departments'])
    )
  }
}
