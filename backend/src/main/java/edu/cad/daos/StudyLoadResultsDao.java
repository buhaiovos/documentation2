package edu.cad.daos;

import edu.cad.entities.EducationForm;
import edu.cad.entities.SubjectInfo;
import edu.cad.entities.SubjectStudyLoad;
import edu.cad.utils.k3.SourceOfFinancing;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Optional;

public class StudyLoadResultsDao extends HibernateDao<SubjectStudyLoad> {
    public StudyLoadResultsDao() {
        super(SubjectStudyLoad.class);
    }

    public Optional<SubjectStudyLoad> findBySubjectInfoAndSourceOfFinanceAndFormOfEducation(SubjectInfo subjectInfo,
                                                                                            SourceOfFinancing sourceOfFinancing,
                                                                                            EducationForm educationForm) {
        EntityManager em = getSession().getEntityManagerFactory().createEntityManager();
        final TypedQuery<SubjectStudyLoad> query = em.createQuery(
                "select slr from SubjectStudyLoad slr " +
                        "where slr.subjectInfo=:subjectInfo " +
                        "and slr.sourceOfFinancing=:source " +
                        "and slr.educationForm=:educationForm",
                SubjectStudyLoad.class);

        query.setParameter("subjectInfo", subjectInfo);
        query.setParameter("source", sourceOfFinancing);
        query.setParameter("educationForm", educationForm);

        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException exc) {
            return Optional.empty();
        }
    }
}
