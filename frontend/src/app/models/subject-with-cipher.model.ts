export class SubjectWithCipher {
  constructor(public cipher: string,
              public id: number,
              public denotation: string,
              public semester: number,
              public ects: number,
              public lectures: number,
              public practices: number,
              public labs: number) {
  }
}
