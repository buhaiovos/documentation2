export class StateCertification {
  constructor(public id: number,
              public semester: number,
              public form: string,
              public start: Date,
              public finish: Date) {
  }

  static empty(): StateCertification {
    return new StateCertification(null, null, null, null, null);
  }
}
