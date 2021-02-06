import { Component, OnInit } from '@angular/core';
import { CurriculumService } from "./curriculum.service";
import { ActivatedRoute, ParamMap, Router } from "@angular/router";
import { Curriculum } from "../../models/curriculum.model";
import { mergeMap, take } from "rxjs/operators";
import { Observable } from "rxjs";
import { Utils } from "../../util/utils";

@Component({
  selector: 'app-curriculum',
  templateUrl: './curriculum.component.html',
  styleUrls: ['./curriculum.component.css'],
  providers: [ CurriculumService ]
})
export class CurriculumComponent implements OnInit {

  public curriculum: Curriculum;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private service: CurriculumService) {
  }

  ngOnInit(): void {
    this.route.paramMap.pipe(
      mergeMap(params => this.getCurriculum(params)),
      take(1)
    ).subscribe(
      curriculum => this.curriculum = curriculum
    )
  }

  save() {
    Utils.takeOne$(this.service.save(this.curriculum))
      .subscribe(
        () => this.router.navigate(['curricula']).then(Utils.noopFunction)
      );
  }

  private getCurriculum(params: ParamMap): Observable<Curriculum> {
    const id = params.get('id');
    return id ? this.service.getById(+id) : this.service.save(Curriculum.empty());
  }
}
