import { Component, OnInit } from '@angular/core';
import { SubjectLoadService } from '../../services/subject-load.service';
import { Load } from '../../models/load.model';
import { MatRadioChange } from '@angular/material/radio';
import { MatTableDataSource } from '@angular/material/table';
import { SubjectLoadComponentService } from './subject-load-component.service';

@Component({
  selector: 'app-subject-load-table-component',
  templateUrl: './subject-load-table.component.html',
  styleUrls: ['./subject-load-table.component.css'],
  providers: [
    SubjectLoadService,
    SubjectLoadComponentService
  ]
})
export class SubjectLoadTableComponent implements OnInit {
  distributionTypes: string[] = [
    'name', 'lectures', 'practices',
    'labs', 'individuals', 'exams', 'credits',
    'contr_works', 'course_projs', 'course_works',
    'rgr', 'dkr', 'referats', 'consult'
  ];
  displayedColumns: string[] = ['id'].concat(this.distributionTypes);

  dataSource: MatTableDataSource<Load>;

  selectedElements = new Map<Load, string[]>();
  semester = '1';
  finance = 'Budgetary';
  educationForm = 'INTRAMURAL';
  distributionMode = 'SUBJECT';

  constructor(private subjectLoadService: SubjectLoadService,
              private subjectLoadComponentService: SubjectLoadComponentService) {
  }

  ngOnInit() {
    console.log(`loading with semester: ${this.semester} and source: ${this.finance}`);
    this.subjectLoadService.fetchSubjectLoad(this.semester, this.educationForm, this.finance)
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
    console.log(`REload with semester: ${this.semester} and source: ${this.finance}`);
    this.selectedElements.clear();
    this.subjectLoadService.fetchSubjectLoad(this.semester, this.educationForm, this.finance)
      .subscribe(allLoad => {
        this.dataSource = new MatTableDataSource<Load>(allLoad);
      });
  }

  semesterClick(event: MatRadioChange) {
    console.log(this.semester);
    this.reload();
  }

  financeClick(event: MatRadioChange) {
    console.log(this.finance);
    this.reload();
  }

  showDistributor(): boolean {
    return this.subjectLoadComponentService.showDistributor(this.selectedElements);
  }

  subjectTitleClicked(element: Load) {
    console.log(`clicked ${element}`);
    this.selectedElements.clear();
  }
}
