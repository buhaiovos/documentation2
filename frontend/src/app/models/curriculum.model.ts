import { SubjectWithCipher } from "./subject-with-cipher.model";

export class Curriculum {
  constructor(public id: number,
              public denotation: string,
              public subjectsWithCiphers: SubjectWithCipher[],
              public displaySubjects: boolean) {
  }

  public static empty() {
    return new Curriculum(
      null,
      'Введіть назву',
      [],
      null
    )
  }
}
