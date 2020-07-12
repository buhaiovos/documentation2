import {
  ChangeDetectionStrategy,
  ChangeDetectorRef,
  Component,
  EventEmitter,
  Input,
  OnChanges,
  OnDestroy,
  OnInit,
  Output,
  SimpleChanges
} from '@angular/core';
import { Load } from '../../models/load.model';
import { Staff } from '../../models/staff.model';
import { StaffService } from '../../services/staff.service';
import { Subscription } from 'rxjs';
import { LoadDistributorComponentService } from './load-distributor-component.service';
import { FormControl } from '@angular/forms';
import { SubjectLoadService } from '../../services/subject-load.service';
import { OtherLoadService } from '../../services/other-load.service';

@Component({
  selector: 'app-load-distributor',
  templateUrl: './load-distributor.component.html',
  styleUrls: ['./load-distributor.component.css'],
  providers: [
    StaffService,
    LoadDistributorComponentService,
    SubjectLoadService,
    OtherLoadService
  ],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class LoadDistributorComponent implements OnInit, OnDestroy, OnChanges {

  @Input()
  objectsOfDistribution: Map<Load, string[]>;
  @Input()
  mode: String;

  @Output()
  submitted = new EventEmitter<any>();

  selectedStaff: Staff;

  allStaff: Staff[];

  changedInput = new FormControl();

  private sub: Subscription;
  private distributedLoad = new Map<number, Map<string, number>>();

  constructor(private staffService: StaffService,
              private loadDistributorComponentService: LoadDistributorComponentService,
              private subjectLoadService: SubjectLoadService,
              private otherLoadService: OtherLoadService,
              private cdRef: ChangeDetectorRef) {
  }

  ngOnInit() {
    this.sub = this.staffService.getAll().subscribe(res => this.allStaff = [...res]);
  }

  submit(): void {
    for (const id of Array.from(this.distributedLoad.keys())) {
      const typeToAmountForGivenId = this.distributedLoad.get(id);
      for (const type of Array.from(this.distributedLoad.get(id).keys())) {
        const value = typeToAmountForGivenId.get(type);
        if (this.mode === 'OTHER') {
          this.otherLoadService.submitOtherLoad(id, this.selectedStaff.id, value)
            .subscribe(() => this.submitted.emit());
        } else {
          this.subjectLoadService.submit(id, type, value, this.selectedStaff.id)
            .subscribe(() => this.submitted.emit());
        }
      }
    }
  }

  ngOnDestroy(): void {
    this.sub.unsubscribe();
  }

  getTypeStringFrom(type: string): string {
    return this.loadDistributorComponentService.getTypeName(type);
  }

  ngOnChanges(changes: SimpleChanges): void {
    this.cdRef.detectChanges();
  }

  submitDistribution(load: Load, type: string) {
    if (this.distributedLoad.has(load.id)) {
      this.distributedLoad.get(load.id).set(type, this.changedInput.value);
    } else {
      const typeToLoadValue = new Map<string, number>();
      typeToLoadValue.set(type, this.changedInput.value);
      this.distributedLoad.set(load.id, typeToLoadValue);
    }
    console.log(`load map ${JSON.stringify(this.distributedLoad.size)}`);
  }
}
