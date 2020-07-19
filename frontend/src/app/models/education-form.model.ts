export class EducationForm {
  constructor(public id: number,
              public denotation: string) {
  }

  empty(): EducationForm {
    return new EducationForm(null, 'Введіть назву')
  }
}
