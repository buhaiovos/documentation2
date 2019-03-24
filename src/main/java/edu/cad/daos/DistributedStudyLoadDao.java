package edu.cad.daos;

import edu.cad.entities.DistributedSubjectStudyLoad;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;

public class DistributedStudyLoadDao extends HibernateDao<DistributedSubjectStudyLoad> {
    public DistributedStudyLoadDao(Class<DistributedSubjectStudyLoad> typeParameterClass) {
        super(typeParameterClass);
    }

    public List<DistributedSubjectStudyLoad> getAllByTargetLoadId(int id) {
        EntityManager em = getSession().getEntityManagerFactory().createEntityManager();
        final TypedQuery<DistributedSubjectStudyLoad> query = em.createQuery(
                "select dssl from DistributedSubjectStudyLoad dssl " +
                        "where dssl.targetLoad.id = :id",
                DistributedSubjectStudyLoad.class);

        query.setParameter("id", id);

        try {
            return query.getResultList();
        } catch (NoResultException exc) {
            return Collections.emptyList();
        }
    }
}
