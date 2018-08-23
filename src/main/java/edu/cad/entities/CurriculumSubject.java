package edu.cad.entities;

import com.google.gson.annotations.Expose;
import edu.cad.entities.interfaces.IDatabaseEntity;
import edu.cad.utils.Utils;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;

@Entity
@Table(name = "curriculum_subject")
@AssociationOverrides({
    @AssociationOverride(name = "pk.curriculum",
	joinColumns = @JoinColumn(name = "id_curriculum")),
    @AssociationOverride(name = "pk.subject",
	joinColumns = @JoinColumn(name = "id_subject")) })
public class CurriculumSubject implements IDatabaseEntity, Serializable,
        Comparable<CurriculumSubject> {
    
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
    
    @Override
    public int getId(){ 
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
    @Override
    public void setId(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
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
    public Subject getSubject() {
        return pk.getSubject();
    }
    
    public void setSubject(Subject subject) {
        pk.setSubject(subject);
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
        if (this.pk != other.getPk()) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(CurriculumSubject other) {
        if(pk.getCurriculum() instanceof Workplan){
            if(Utils.isParseable(cipher) && Utils.isParseable(other.getCipher())){
                return Integer.parseInt(cipher) - Integer.parseInt(other.getCipher());
            }
        }
        
        return cipher.compareTo(other.getCipher());
    }
}
