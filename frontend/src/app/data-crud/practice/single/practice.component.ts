import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from "@angular/router";
import { PracticeService } from "../practice.service";

@Component({
  selector: 'app-practice',
  templateUrl: './practice.component.html',
  styleUrls: ['./practice.component.css'],
  providers: [PracticeService]
})
export class PracticeComponent implements OnInit {

  constructor(private route: ActivatedRoute,
              private router: Router,
              private service: PracticeService) { }

  ngOnInit(): void {
  }

}
