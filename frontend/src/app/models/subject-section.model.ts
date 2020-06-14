import { DropdownOption } from "./dropdown-option.model";

export class SubjectSection {
  constructor(public id: number,
              public denotation: string,
              public optional: boolean,
              public cycle: DropdownOption) {
  }

  static empty(): SubjectSection {
    return new SubjectSection(
      null,
      'Введіть назву',
      false,
      DropdownOption.empty()
    )
  }
}
