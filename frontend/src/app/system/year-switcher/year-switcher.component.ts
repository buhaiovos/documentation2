import { Component, OnInit } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { forkJoin } from "rxjs";

@Component({
  selector: 'app-year-switcher',
  templateUrl: './year-switcher.component.html',
  styleUrls: ['./year-switcher.component.css']
})
export class YearSwitcherComponent implements OnInit {

  years: [number];
  current: number;

  constructor(private http: HttpClient) {
  }

  ngOnInit(): void {
    const years$ = this.http.get<[number]>('/v2/years');
    const current$ = this.http.get<YearDto>('/v2/years/current');

    forkJoin({
      years: years$,
      current: current$
    }).subscribe(res => {
      this.years = res.years;
      this.current = res.current.year;
    })
  }

  selectYear() {
    this.http.post<YearDto>('/v2/years/current',
      {
        year: this.current
      }
    ).subscribe(() => {
    })
  }


}

export class YearDto {
  constructor(public year: number) {
  }
}
