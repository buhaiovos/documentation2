export class DropdownOption {
  constructor(public id: number,
              public text: string) {
  }

  public static empty: () => DropdownOption = () => new DropdownOption(null, null);
}
