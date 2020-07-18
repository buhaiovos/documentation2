export class DiplomaPrepWorkType {
  constructor(public id: number,
              public denotation: string) {
  }

  static empty(): DiplomaPrepWorkType {
    return new DiplomaPrepWorkType(null, 'Введіть назву');
  }
}
