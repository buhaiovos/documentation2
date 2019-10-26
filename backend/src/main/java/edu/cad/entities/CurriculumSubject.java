package edu.cad.entities;

import com.google.gson.annotations.Expose;
import edu.cad.entities.interfaces.IDatabaseEntity;
import edu.cad.utils.Utils;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
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

    @Expose
    @Column(name = "cipher")
    private String cipher;

    public CurriculumSubject() {
    }

    public CurriculumSubject(String cipher) {
        this.cipher = cipher;
    }

    public CurriculumSubjectId getPk() {
        return pk;
    }

    public void setPk(CurriculumSubjectId pk) {
        this.pk = pk;
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

    public String getCipher() {
        return cipher;
    }

    public void setCipher(String cipher) {
        this.cipher = cipher;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.pk);
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
        /*if (getClass() != obj.getClass()) {
            return false;
        }*/
        final CurriculumSubject other = (CurriculumSubject) obj;
        return this.pk == other.getPk();
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
