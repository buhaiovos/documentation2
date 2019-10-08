package edu.cad.daos;

import edu.cad.entities.interfaces.IDatabaseEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("unchecked")
public class HibernateDao<Entity extends IDatabaseEntity> implements IDAO<Entity> {
    private final Class<Entity> typeParameterClass;
    private final Session session = null;

    public HibernateDao(Class<Entity> typeParameterClass) {
        this.typeParameterClass = typeParameterClass;
    }

    public HibernateDao(Class<Entity> typeParameterClass, Session session) {
        this.typeParameterClass = typeParameterClass;
    }

    @Override
    public List<Entity> getAll() {
//        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
//        CriteriaQuery<Entity> query = criteriaBuilder.createQuery(typeParameterClass);
//        Root<Entity> fromT = query.from(typeParameterClass);
//        query.select(fromT);
//        TypedQuery<Entity> typedQuery = session.createQuery(query);
//        List<Entity> resultList = typedQuery.getResultList();
//
//        if (typeParameterClass.equals(Curriculum.class)) {
//            resultList.removeIf(element -> element instanceof WorkingPlan);
//        }
//
        return Collections.emptyList();
    }

    @Override
    public Entity get(int id) {
        return session.get(typeParameterClass, id);
    }

    @Override
    public Optional<Entity> getById(int id) {
        return Optional.ofNullable(get(id));
    }

    @Override
    public Entity update(Entity instance) {
        Transaction transaction = session.beginTransaction();

        try {
            session.merge(instance);
        } catch (RuntimeException e) {
            transaction.rollback();
            throw e;
        } finally {
            session.flush();
            transaction.commit();
        }
        return instance;
    }

    @Override
    public boolean create(Entity instance) {
        Transaction transaction = session.beginTransaction();
        try {
            session.save(instance);
            session.flush();
            transaction.commit();
            session.clear();
        } catch (RuntimeException e) {
            System.out.println(e);
            transaction.rollback();
            return false;
        } finally {
            if (transaction.getStatus().equals(TransactionStatus.ACTIVE)) {
                transaction.commit();
            }
        }

        return true;
    }

    @Override
    public boolean delete(int id) {
        Transaction transaction = session.beginTransaction();

        try {
            Entity instance = session.load(typeParameterClass, id);
            session.delete(instance);
            transaction.commit();
        } catch (RuntimeException e) {
            transaction.rollback();
            return false;
        } finally {
//            session.flush();
        }

        return true;
    }

    Session getSession() {
        return this.session;
    }
}
