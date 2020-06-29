import { Component, OnInit } from '@angular/core';
import { Department } from "../../models/department.model";
import { DepartmentService } from "../department/department.service";
import { Router } from "@angular/router";
import { Utils } from "../../util/utils";
import { take } from "rxjs/operators";

@Component({
  selector: 'app-department-list',
  templateUrl: './department-list.component.html',
  styleUrls: ['./department-list.component.css'],
  providers: [DepartmentService]
})
export class DepartmentListComponent implements OnInit {
  departments: Department[];

  constructor(private router: Router,
              private service: DepartmentService) {
  }

  ngOnInit(): void {
    Utils.takeOne$(
      this.service.getAll()
    ).subscribe(departments => this.departments = departments)
  }

  create() {
    this.router.navigate(['/department']).then(Utils.noopFunction);
  }

  edit(id: number) {
    this.router.navigate(['/department', {id: id}]).then(Utils.noopFunction);
  }

  delete(id: number) {
    this.service.deleteById(id)
      .pipe(take(1))
      .subscribe(() => this.removeFromList(id));
  }

  private removeFromList(id: number) {
    this.departments = this.departments.filter(d => d.id !== id);
  }
}
