import { Component, OnInit } from '@angular/core';
import { Group } from "../../models/group.model";
import { GroupService } from "../group/group.service";
import { Router } from "@angular/router";
import { Utils } from "../../util/utils";
import { take } from "rxjs/operators";

@Component({
  selector: 'app-groups-list',
  templateUrl: './groups-list.component.html',
  styleUrls: ['./groups-list.component.css'],
  providers: [GroupService]
})
export class GroupsListComponent implements OnInit {

  groups: Group[];

  constructor(private service: GroupService,
              private router: Router) {
  }

  ngOnInit(): void {
    Utils.takeOne$(
      this.service.getAll()
    ).subscribe(groups => this.groups = groups)
  }

  create() {
    this.router.navigate(['/group']).then(Utils.noopFunction);
  }

  edit(id: number) {
    this.router.navigate(['/group', {id: id}]).then(Utils.noopFunction);
  }

  delete(id: number) {
    this.service.deleteById(id)
      .pipe(take(1))
      .subscribe(() => this.removeFromList(id));
  }

  private removeFromList(id: number) {
    this.groups = this.groups.filter(d => d.id !== id);
  }

}
