export class Department {
  constructor(public id: number,
              public denotation: string) {
  }

  static empty() {
    return new Department(null, 'Введіть назву');
  }
}
