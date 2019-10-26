package edu.cad.daos;

import edu.cad.domain.ObjectOfWork;
import edu.cad.domain.OtherLoadType;
import edu.cad.entities.OtherLoad;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Optional;

public class OtherLoadDao extends HibernateDao<OtherLoad> {

    public OtherLoadDao() {
        super(OtherLoad.class);
    }

    public Optional<OtherLoad> findByLoadTypeAndWorkObject(OtherLoadType type, ObjectOfWork object) {
        EntityManager em = getSession().getEntityManagerFactory().createEntityManager();
        TypedQuery<OtherLoad> query = em.createQuery("select ol from OtherLoad ol " +
                "where ol.loadType = :loadType " +
                "and ol.objectOfWork = :objectOfWork", OtherLoad.class);

        query.setParameter("loadType", type);
        query.setParameter("objectOfWork", object);

        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
