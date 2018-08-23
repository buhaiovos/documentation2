package edu.cad.daos;

import edu.cad.entities.Curriculum;
import edu.cad.entities.Workplan;
import edu.cad.entities.interfaces.IDatabaseEntity;
import edu.cad.utils.hibernateutils.HibernateSession;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

@SuppressWarnings("unchecked")
public class HibernateDAO<T extends IDatabaseEntity> implements IDAO<T>{	
    private final Class<T> typeParameterClass;
    private final Session session;
    
    public HibernateDAO(Class<T> typeParameterClass){
        this.typeParameterClass = typeParameterClass;
        this.session = HibernateSession.getInstance();
    }

    public HibernateDAO(Class<T> typeParameterClass, Session session){
        this.typeParameterClass = typeParameterClass;
        this.session = session;
    }
    
    @Override
    public List<T> getAll() {
	//Session session = factory.openSession();
        List<T> list = session.createCriteria(typeParameterClass).list();
        
        
        if(typeParameterClass.equals(Curriculum.class)){
            Iterator<T> iterator = list.iterator();
            while(iterator.hasNext()){
                T element = iterator.next();
                if(element instanceof Workplan){
                    iterator.remove();
                }
            }
        }
        
        //session.close();
        
        
        return list;
    }

    @Override
    public T get(int id) {
        //Session session = factory.openSession(); 
	T instance = (T) session.get(typeParameterClass, id);
	//session.close();
		
	return instance;
    }

    @Override
    public T update(T instance) {
        //Session session = factory.openSession();  
        Transaction transaction = session.beginTransaction();  
        
        try {
            session.merge(instance); 
            transaction.commit();
        } catch(RuntimeException e) {
            transaction.rollback();
        } finally {
            session.flush();
            //session.close();
        }
        
        return instance;
    }

    @Override
    public boolean create(T instance) {
	//Session session = factory.openSession();  
        Transaction transaction = session.beginTransaction();  
        try {
            session.save(instance); 
            session.flush();
            transaction.commit();
            //session.flush();
            session.clear();
            //session.flush();
        } catch(RuntimeException e) {
            System.out.println(e);
            transaction.rollback();
            return false;
        } finally {
            //session.flush();
            //session.close();
        }
        
        return true;
    }

    @Override
    public boolean delete(int id) {
	//Session session = factory.openSession(); 
        Transaction transaction = session.beginTransaction(); 
        
        try {
            T instance = (T) session.load(typeParameterClass, id);
            session.delete(instance);
            transaction.commit();
        } catch(RuntimeException e) {
            transaction.rollback();
            return false;
        } finally {
            session.flush();
            //session.close();
        }
			
	return true;
    }	
}