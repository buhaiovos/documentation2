import { SubjectWithCipher } from "./subject-with-cipher.model";
import { Curriculum } from "./curriculum.model";
import { DropdownOption } from "./dropdown-option.model";

export class WorkingPlan extends Curriculum {
  constructor(id: number,
              denotation: string,
              subjectsWithCiphers: SubjectWithCipher[],
              displaySubjects: boolean,
              public curriculum: DropdownOption,
              public practice: DropdownOption,
              public stateCertification: DropdownOption,
              public scientificResearchSubject: DropdownOption,
              public groups: DropdownOption[],
              public diplomaPreparations: DropdownOption[]) {
    super(id, denotation, subjectsWithCiphers, displaySubjects);
  }
}
