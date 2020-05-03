export class SubjectInfo {
  constructor(public subjectHeaderId: number,
              public id?: number,
              public semester?: number,
              public semestersDuration?: number,
              public lectures?: number,
              public actualLectures?: number,
              public labs?: number,
              public actualLabs?: number,
              public practices?: number,
              public actualPractices?: number,
              public ects?: number,
              public controlsIds?: number[]) {
  }
}
