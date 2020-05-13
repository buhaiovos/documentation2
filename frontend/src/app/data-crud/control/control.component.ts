import { Component, OnInit } from '@angular/core';
import { Control } from "../../models/control.model";
import { ActivatedRoute, ParamMap, Router } from "@angular/router";
import { ControlService } from "./control.service";
import { flatMap, take } from "rxjs/operators";
import { forkJoin, Observable } from "rxjs";
import { DropdownOption } from "../../models/dropdown-option.model";

@Component({
  selector: 'app-control',
  templateUrl: './control.component.html',
  styleUrls: ['./control.component.css'],
  providers: [ControlService]
})
export class ControlComponent implements OnInit {


  controlTypes: DropdownOption[];

  control: Control;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private service: ControlService) {
  }

  ngOnInit(): void {
    const types = this.service.getTypes().pipe(take(1));
    const control = this.route.paramMap.pipe(
      flatMap(param => this.getControl(param)),
      take(1)
    );

    this.initializeFields(types, control);
  }

  private initializeFields(types: Observable<DropdownOption[]>, control: Observable<Control>) {
    forkJoin({
        types: types,
        control: control
      }
    ).subscribe(result => {
        this.controlTypes = result.types;
        this.control = result.control;
      }
    );
  }

  private getControl(params: ParamMap): Observable<Control> {
    const id = params.get('id');
    const subjectId = params.get('subjectId');

    return id ? this.service.getById(+id) : this.service.save(new Control(+subjectId));
  }

  save() {
    this.service.save(this.control)
      .pipe(take(1))
      .subscribe(control => this.router.navigate(['/subject-infos', {id: control.subjectId}]));
  }
}
