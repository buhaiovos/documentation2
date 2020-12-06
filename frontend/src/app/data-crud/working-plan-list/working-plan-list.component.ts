import { Component, OnInit } from '@angular/core';
import { Router } from "@angular/router";
import { Utils } from "../../util/utils";
import { WorkingPlan } from "../../models/working-plan.model";
import { WorkingPlanService } from "../working-plan/working-plan.service";
import { DropdownOption } from "../../models/dropdown-option.model";

@Component({
  selector: 'app-working-plan-list',
  templateUrl: './working-plan-list.component.html',
  styleUrls: ['./working-plan-list.component.css'],
  providers: [WorkingPlanService]
})
export class WorkingPlanListComponent implements OnInit {

  public workingPlan: WorkingPlan[];

  constructor(private service: WorkingPlanService,
              private router: Router) {
  }

  ngOnInit(): void {
    Utils.takeOne$(this.service.getAll())
      .subscribe(c => this.workingPlan = c);
  }

  edit(wp: WorkingPlan): void {
    this.router
      .navigate(['/working-plan', {id: wp.id}])
      .then(Utils.noopFunction)
  }

  delete(wp: WorkingPlan): void {
    // this.service.deleteById(wp.id)
  }

  addSubject(wp: WorkingPlan): void {
    this.router
      .navigate(['/curriculum-subject-list-selection', {id: wp.id}])
      .then(Utils.noopFunction);
  }

  formatArray(groups: DropdownOption[]): string {
    return groups.map(option => option.text).join(', ');
  }
}
