import { Component, OnInit } from '@angular/core';
import { SubjectSectionService } from "../subject-section-list/subject-section.service";
import { ActivatedRoute, Router } from "@angular/router";
import { SubjectSection } from "../../models/subject-section.model";
import { DropdownOption } from "../../models/dropdown-option.model";
import { forkJoin, Observable } from "rxjs";
import { Utils } from "../../util/utils";
import { flatMap } from "rxjs/operators";
import { SubjectCycleService } from "../cycle/subject-cycle.service";

@Component({
  selector: 'app-subject-section',
  templateUrl: './subject-section.component.html',
  styleUrls: ['./subject-section.component.css'],
  providers: [SubjectSectionService, SubjectCycleService]
})
export class SubjectSectionComponent implements OnInit {

  section: SubjectSection;
  cycles: DropdownOption[];

  constructor(private service: SubjectSectionService,
              private cycleService: SubjectCycleService,
              private router: Router,
              private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    const section$: Observable<SubjectSection> = Utils.takeOne$(this.getSection());
    const cycles$: Observable<DropdownOption[]> = Utils.takeOne$(this.cycleService.getOptions());

    forkJoin({
      section: section$,
      cycles: cycles$
    }).subscribe(
      result => {
        this.section = result.section;
        this.cycles = result.cycles;
      }
    )
  }

  save() {
    const result: Observable<SubjectSection> = this.createOrUpdate();
    Utils.takeOne$(result)
      .subscribe(ignored => this.router.navigate(['/sections']));
  }

  private createOrUpdate(): Observable<SubjectSection> {
    return (this.section.id)
      ? this.service.update(this.section)
      : this.service.create(this.section);
  }

  private getSection(): Observable<SubjectSection> {
    const fetchFunction: (id: number) => Observable<SubjectSection> = id => this.service.getById(id);
    const createFunction: () => SubjectSection = () => SubjectSection.empty();
    return this.route.paramMap.pipe(
      flatMap(params => Utils.fetchOrCreate$(fetchFunction, createFunction, params.get('id')))
    );
  }
}
