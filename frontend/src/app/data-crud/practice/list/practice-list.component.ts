import { Component, OnInit } from '@angular/core';
import { Practice } from "../../../models/practice.model";
import { Router } from "@angular/router";
import { PracticeService } from "../practice.service";
import { Utils } from "../../../util/utils";

@Component({
  selector: 'app-practice-list',
  templateUrl: './practice-list.component.html',
  styleUrls: ['./practice-list.component.css'],
  providers: [PracticeService]
})
export class PracticeListComponent implements OnInit {
  practices: Practice[];

  constructor(private router: Router,
              private service: PracticeService) {
  }

  ngOnInit(): void {
    Utils.takeOne$(this.service.getAll())
      .subscribe(all => this.practices = all);
  }

  create(): void {
    this.router.navigate(['/practice']).then(Utils.noopFunction);
  }

  edit(id: number): void {
    this.router.navigate(['/practice', {id: id}]).then(Utils.noopFunction);
  }

  delete(id: number): void {
    Utils.takeOne$(this.service.deleteById(id))
      .subscribe(() => this.router.navigate(['/practices']));
  }
}
