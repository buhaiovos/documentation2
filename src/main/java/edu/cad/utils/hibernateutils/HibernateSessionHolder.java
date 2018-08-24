package edu.cad.utils.hibernateutils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.cfgxml.spi.LoadedConfig;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public enum HibernateSessionHolder {
    INSTANCE;

    private static final String HIBERNATE_CFG_XML = "hibernate.cfg.xml";

    private Session session;
    private Configuration configuration;


    public synchronized Session getSession() {
        if ((session == null) || !session.isOpen()) {
            openSession();
        }
        return session;
    }

    private synchronized void openSession() {
        this.configuration = new Configuration();
        this.configuration.configure(HIBERNATE_CFG_XML);
        this.session = getSessionFactory().openSession();
    }

    private synchronized SessionFactory getSessionFactory() {
        final LoadedConfig aggregatedCfgXml = configuration.getStandardServiceRegistryBuilder().getAggregatedCfgXml();
        final ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .configure(aggregatedCfgXml)
                .applySettings(configuration.getProperties())
                .build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    public synchronized void openNewConfiguredSession(Configuration configuration) {
        this.configuration = configuration;
        this.session = getSessionFactory().openSession();
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void close() {
        if ((session != null) && session.isOpen()) {
            session.close();
        }
    }
}
