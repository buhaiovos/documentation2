import { DropdownOption } from "./dropdown-option.model";

export class DiplomaPreparation {
  constructor(public id: number,
              public workType: DropdownOption,
              public norm: number,
              public department: DropdownOption,
              public workingPlan: DropdownOption) {
  }

  public static empty(): DiplomaPreparation {
    return new DiplomaPreparation(
      null,
      DropdownOption.empty(),
      0.0,
      DropdownOption.empty(),
      DropdownOption.empty()
    )
  }

}
