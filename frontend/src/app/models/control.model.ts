export class Control {
  constructor(
    public subjectId: number,
    public id: number = null,
    public semester?: number,
    public typeId?: number,
    public name?: string) {
  }
}
