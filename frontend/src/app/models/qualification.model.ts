export class Qualification {
  public constructor(public id: number,
                     public denotation: string) {
  }

  static empty(): Qualification {
    return new Qualification(null, "Введіть назву");
  }
}
