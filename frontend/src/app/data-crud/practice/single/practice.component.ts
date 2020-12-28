import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from "@angular/router";
import { PracticeService } from "../practice.service";
import { Utils } from "../../../util/utils";
import { map, mergeMap } from "rxjs/operators";
import { Practice } from "../../../models/practice.model";
import { Observable, of } from "rxjs";

@Component({
  selector: 'app-practice',
  templateUrl: './practice.component.html',
  styleUrls: ['./practice.component.css'],
  providers: [PracticeService]
})
export class PracticeComponent implements OnInit {

  practiceTypes: string[] = [
    'Переддипломна',
    'Педагогічна',
    'Науково-дослідна'
  ]

  practice: Practice;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private service: PracticeService) { }

  ngOnInit(): void {
    Utils.takeOne$(this.route.paramMap)
      .pipe(
        map(params => params.get('id')),
        mergeMap(id => this.getPractice(id))
      )
      .subscribe(p => this.practice = p);
  }

  save(): void {
    Utils.takeOne$(this.service.save(this.practice))
      .subscribe(() => this.router.navigate(['practices']));
  }

  private getPractice(id: string): Observable<Practice> {
    return id ? this.service.getById(+id) : of(Practice.empty())
  }
}
