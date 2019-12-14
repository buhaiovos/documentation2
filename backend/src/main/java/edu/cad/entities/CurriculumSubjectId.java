package edu.cad.entities;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CurriculumSubjectId implements Serializable {

    @ManyToOne
    private Curriculum curriculum;

    @ManyToOne
    private SubjectInfo subjectInfo;

    public CurriculumSubjectId() {
    }

    public CurriculumSubjectId(Curriculum curriculum, SubjectInfo subjectInfo) {
        this.curriculum = curriculum;
        this.subjectInfo = subjectInfo;
    }

    public Curriculum getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(Curriculum curriculum) {
        this.curriculum = curriculum;
    }

    public SubjectInfo getSubjectInfo() {
        return subjectInfo;
    }

    public void setSubjectInfo(SubjectInfo subjectInfo) {
        this.subjectInfo = subjectInfo;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.curriculum);
        hash = 97 * hash + Objects.hashCode(this.subjectInfo);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        final CurriculumSubjectId other = (CurriculumSubjectId) obj;
        if (!this.curriculum.equals(other.getCurriculum())) {
            return false;
        }
        return this.subjectInfo.equals(other.getSubjectInfo());
    }
}
