import {Component, OnInit} from '@angular/core';
import {MatTableDataSource} from '@angular/material';
import {Load} from '../../models/load.model';
import {OtherLoadService} from '../../services/other-load.service';
import {SubjectLoadComponentService} from '../subject-load-table/subject-load-component.service';

@Component({
  selector: 'app-other-load-table-component',
  templateUrl: './other-load-table.component.html',
  styleUrls: ['./other-load-table.component.css'],
  providers: [
    OtherLoadService,
    SubjectLoadComponentService
  ]
})
export class OtherLoadTableComponent implements OnInit {

  displayedColumns: string[] = ['id', 'typeOfActivity', 'object', 'hours'];
  dataSource: MatTableDataSource<Load>;
  selectedElements = new Map<Load, string[]>();
  semester = '1';
  finance = 'Budgetary';
  educationForm = 'INTRAMURAL';
  distributionMode = 'OTHER';

  constructor(private otherLoadService: OtherLoadService,
              private subjectLoadComponentService: SubjectLoadComponentService) {
  }

  ngOnInit() {
    console.log(`loading with semester: ${this.semester} and source: ${this.finance}`);
    this.otherLoadService.fetchOtherLoad(+this.semester, this.educationForm, this.finance)
      .subscribe(allLoad => {
        this.dataSource = new MatTableDataSource<Load>(allLoad);
      });
  }

  loadItemClicked(selectedType: string, selectedElement: Load): void {
    this.subjectLoadComponentService.addOrRemoveElementFromSelected(
      selectedType, selectedElement, this.selectedElements
    );
  }

  isSelected(type: string, element: Load) {
    return this.subjectLoadComponentService.isSelected(type, element, this.selectedElements);
  }

  applyFilter(filterValue: string): void {
    this.dataSource.filter = filterValue.toLowerCase().trim();
  }

  hasError(load: Load) {
    return this.subjectLoadComponentService.hasError(load);
  }

  isDone(load: Load) {
    return this.subjectLoadComponentService.isDone(load);
  }

  reload() {
    console.log(`reload with semester: ${this.semester} and source: ${this.finance}`);
    this.selectedElements.clear();
    this.otherLoadService.fetchOtherLoad(+this.semester, this.educationForm, this.finance)
      .subscribe(allLoad => {
        this.dataSource = new MatTableDataSource<Load>(allLoad);
      });
  }

  semesterClick(e) {
    console.log(this.semester);
    this.reload();
  }

  financeClick(e) {
    console.log(this.finance);
    this.reload();
  }

  showDistributor() {
    return this.selectedElements.size > 0;
  }

  subjectTitleClicked(element: Load) {
    console.log(`clicked ${element}`);
    this.selectedElements.clear();
  }

  getObjectOfActivity(load: Load): string {
    return Object.keys(load.elements).find(key => key != null);
  }

  getHours(load: Load): number {
    return Object.keys(load.elements)
      .map(key => load.elements[key])
      .find(value => value);
  }
}
