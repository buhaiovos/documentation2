package edu.cad.daos;

import edu.cad.entities.EducationForm;
import edu.cad.entities.OtherLoad;
import edu.cad.entities.OtherLoadInfo;
import edu.cad.utils.k3.SourceOfFinancing;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Optional;

public class OtherLoadInfoDao extends HibernateDao<OtherLoadInfo> {
    public OtherLoadInfoDao() {
        super(OtherLoadInfo.class);
    }

    public Optional<OtherLoadInfo> findByLoadHeaderAndSemesterAndYearAndEducationFormAndFinancialSource(OtherLoad loadHeader,
                                                                                                        int semester,
                                                                                                        int year,
                                                                                                        EducationForm educationForm,
                                                                                                        SourceOfFinancing source) {
        EntityManager em = getSession().getEntityManagerFactory().createEntityManager();
        TypedQuery<OtherLoadInfo> query = em.createQuery("select oli from OtherLoadInfo oli " +
                "where oli.loadHeader = :loadHeader " +
                "and oli.semester = :semester " +
                "and oli.yearOfEducation = :year " +
                "and oli.educationForm = :educationForm " +
                "and oli.sourceOfFinancing = :sourceOfFinancing", OtherLoadInfo.class);

        query.setParameter("loadHeader", loadHeader);
        query.setParameter("semester", semester);
        query.setParameter("year", year);
        query.setParameter("educationForm", educationForm);
        query.setParameter("sourceOfFinancing", source);

        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
