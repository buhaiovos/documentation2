package edu.cad.documentelements.k3columns;

import edu.cad.entities.AcademicGroup;
import edu.cad.utils.k3.K3SubjectEntity;
import edu.cad.utils.k3.SourceOfFinancing;

public class StudentsK3Column extends AbstractK3Column{
    private final SourceOfFinancing groupSource;
    private final SourceOfFinancing studentSource;
    
    public StudentsK3Column(int columnNumber, SourceOfFinancing groupSource,
            SourceOfFinancing studentSource) {
        super(columnNumber);
        this.groupSource = groupSource;
        this.studentSource = studentSource;
    }

    @Override
    public String getValue(K3SubjectEntity subject) {
        int total = 0;

        for (AcademicGroup group : subject.getSubjectInfo().getGroups()) {
            if(groupSource.sourceEquals(group)){
                total += studentSource.getStudents(group);
            }
        }

        return total > 0 ? Integer.toString(total) : "0";
    }
}
