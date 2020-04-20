package edu.cad.entities;

import edu.cad.entities.interfaces.IDatabaseEntity;
import edu.cad.utils.Utils;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode(of = "pk")
@Entity
@Table(name = "curriculum_subject")
@AssociationOverrides({
        @AssociationOverride(
                name = "pk.curriculum",
                joinColumns = @JoinColumn(name = "id_curriculum")
        ),
        @AssociationOverride(
                name = "pk.subjectInfo",
                joinColumns = @JoinColumn(name = "id_subject")
        )
})
public class CurriculumSubject implements IDatabaseEntity<Integer>, Serializable, Comparable<CurriculumSubject> {

    @EmbeddedId
    private CurriculumSubjectId pk = new CurriculumSubjectId();


    @Column(name = "cipher")
    private String cipher;

    public CurriculumSubject() {
    }

    public CurriculumSubject(String cipher) {
        this.cipher = cipher;
    }

    @Transient
    public Curriculum getCurriculum() {
        return pk.getCurriculum();
    }

    public void setCurriculum(Curriculum curriculum) {
        pk.setCurriculum(curriculum);
    }

    @Transient
    public SubjectInfo getSubjectInfo() {
        return pk.getSubjectInfo();
    }

    public void setSubjectInfo(SubjectInfo subjectInfo) {
        pk.setSubjectInfo(subjectInfo);
    }

    @Override
    public int compareTo(CurriculumSubject other) {
        if (pk.getCurriculum() instanceof WorkingPlan) {
            if (Utils.isNumber(cipher) && Utils.isNumber(other.getCipher())) {
                return Integer.parseInt(cipher) - Integer.parseInt(other.getCipher());
            }
        }

        return cipher.compareTo(other.getCipher());
    }

    @Override
    public Integer getId() {
        throw new UnsupportedOperationException("do not use");
    }

    @Override
    public void setIdentifier(Integer id) {
        // do nothing
    }
}
