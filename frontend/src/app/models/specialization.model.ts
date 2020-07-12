import { DropdownOption } from "./dropdown-option.model";

export class Specialization {
  public constructor(public id: number,
                     public denotation: string,
                     public department: DropdownOption) {
  }

  static empty(): Specialization {
    return new Specialization(null, "Введіть назву", DropdownOption.empty());
  }

}
