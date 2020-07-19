import { DropdownOption } from "./dropdown-option.model";

export class Group {
  public constructor(public id: number,
                     public cipher: string,
                     public budgetaryStudents: number,
                     public contractStudents: number,
                     public startYear: number,
                     public specialization: DropdownOption,
                     public qualification: DropdownOption,
                     public educationForm: DropdownOption) {
  }

  static empty() {
    return new Group(
      null,
      'Введіть Шифр',
      0,
      0,
      new Date().getUTCFullYear(),
      DropdownOption.empty(),
      DropdownOption.empty(),
      DropdownOption.empty()
    )
  }
}
