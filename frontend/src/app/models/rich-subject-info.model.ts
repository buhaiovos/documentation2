import { Control } from "./control.model";

export class RichSubjectInfo {
  constructor(public id: number,
              public denotation: string,
              public semester: number,
              public semestersDuration: number,
              public lectures: number,
              public actualLectures: number,
              public labs: number,
              public actualLabs: number,
              public practices: number,
              public actualPractices: number,
              public ects: number,
              public controls: Control[]) {
  }
}
