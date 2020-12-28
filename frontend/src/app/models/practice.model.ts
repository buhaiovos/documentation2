export class Practice {
  constructor(public id: number,
              public semester: number,
              public weeks: number,
              public type: String,
              public start: Date,
              public finish: Date) {
  }

  static empty(): Practice {
    return new Practice(
      null,
      null,
      null,
      null,
      null,
      null
    )
  }
}
