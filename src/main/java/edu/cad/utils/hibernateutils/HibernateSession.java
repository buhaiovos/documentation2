package edu.cad.utils.hibernateutils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateSession{
    private static Session session;
    private static Configuration configuration;
    
    private HibernateSession(){}
    
    private static void openSession(){
        configuration = new Configuration();  
        configuration.configure("hibernate.cfg.xml");  
        
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();
        SessionFactory factory = configuration.buildSessionFactory(serviceRegistry); 
        
        session = factory.openSession();
    }
    
    public static void openSession(Configuration configuration){  
        HibernateSession.configuration = configuration;
        
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();
        SessionFactory factory = configuration.buildSessionFactory(serviceRegistry); 
        
        session = factory.openSession();
    }
    
    public static synchronized Session getInstance(){
        if((session == null) || !session.isOpen()){
            openSession();
        }
        
        return session;
    }
    
    public static Configuration getConfiguration(){
        return configuration;
    }

    public static void close(){
        if((session != null) && session.isOpen()){
           session.close(); 
        }
    }
}
