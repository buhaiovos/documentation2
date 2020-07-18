import { DropdownOption } from "./dropdown-option.model";

export class SubjectHeader {
  constructor(public id: number = null,
              public denotation?: string,
              public superSubject?: DropdownOption,
              public curriculumSection?: DropdownOption,
              public workingPlanSection?: DropdownOption,
              public department?: DropdownOption) {
  }
}
