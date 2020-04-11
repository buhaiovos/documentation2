export class SubjectInfo {
  constructor(public id: number,
              public semester: number,
              public semestersDuration: number,
              public lectures: number,
              public actualLectures: number,
              public labs: number,
              public actualLabs: number,
              public practices: number,
              public actualPractices: number,
              public ects: number,
              public subjectHeaderId: number,
              public controlsIds: number[]) {
  }
}
