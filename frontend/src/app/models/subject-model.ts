import { SubjectInfo } from "./subject-info.model";
import { SubjectHeader } from "./subject-header.model";

export class Subject {
  constructor(public header: SubjectHeader,
              public infos: SubjectInfo[],
              public displayInfo: boolean) {
  }
}
