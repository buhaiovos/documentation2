export class SubjectHeader {
  constructor(public id?: number,
              public denotation?: string,
              public superSubjectId?: number,
              public curriculumSectionId?: number,
              public workingPlanSectionId?: number,
              public subjectTypeId?: number,
              public departmentId?: number) {
  }
}
