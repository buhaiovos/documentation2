package edu.cad.entities;

import com.google.gson.annotations.Expose;
import edu.cad.domain.PracticeType;
import edu.cad.entities.interfaces.IDatabaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "practice")
public class Practice implements IDatabaseEntity, Serializable{
    
    @Expose
    @Id
    @GenericGenerator(
        name = "assigned-identity", 
        strategy = "edu.cad.utils.hibernateutils.AssignedIdentityGenerator"
    )
    @GeneratedValue(generator = "assigned-identity")
    @Column(name = "id", unique = true, nullable = false)
    private int id;
    
    @Expose
    @Column(name = "semester")
    private int semester;
    
    @Expose
    @Column(name = "weeks")
    private int weeks;
    
    @Expose
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private PracticeType type;
    
    @Expose
    @Column(name = "start")
    private Date start;
    
    @Expose
    @Column(name = "finish")
    private Date finish;

    public Practice() {
    }

    public Practice(int id, int semester, int weeks, PracticeType type, Date start, Date finish) {
        this.id = id;
        this.semester = semester;
        this.weeks = weeks;
        this.type = type;
        this.start = start;
        this.finish = finish;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public int getWeeks() {
        return weeks;
    }

    public void setWeeks(int weeks) {
        this.weeks = weeks;
    }

    public PracticeType getType() {
        return type;
    }

    public void setType(PracticeType denotation) {
        this.type = denotation;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getFinish() {
        return finish;
    }

    public void setFinish(Date finish) {
        this.finish = finish;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + this.id;
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
        final Practice other = (Practice) obj;
        return this.id == other.getId();
    }

    @Override
    public String toString() {
        return type + " (" + semester + " семестр/" + weeks + " тижні(в))";
    }

    public void setDenotationFromString(String type) {
        this.type = PracticeType.valueOf(type);
    }
}
