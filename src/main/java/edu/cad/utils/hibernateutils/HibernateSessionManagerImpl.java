package edu.cad.utils.hibernateutils;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.cfgxml.spi.LoadedConfig;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.Map;
import java.util.Properties;

@Slf4j
public enum HibernateSessionManagerImpl implements HibernateSessionManager {
    INSTANCE;

    private static final String HIBERNATE_CFG_XML = "hibernate.cfg.xml";

    private Session session;
    private Configuration configuration;

    @Override
    public synchronized Session getCurrentSession() {
        if ((session == null) || !session.isOpen()) {
            openSession();
        }
        return session;
    }

    private synchronized void openSession() {
        this.configuration = fetchConfiguration();
        SessionFactory sessionFactory = getSessionFactory();
        this.session = sessionFactory.openSession();
    }

    private Configuration fetchConfiguration() {
        var configuration = new Configuration();
        configuration.configure(HIBERNATE_CFG_XML);

        Properties properties = getDatabaseProperties();
        configuration.addProperties(properties);

        return configuration;
    }

    private Properties getDatabaseProperties() {
        Properties properties = new Properties();

        String url = System.getenv("DB_URL");
        String password = System.getenv("DB_PASSWORD");
        String username = System.getenv("DB_USER_NAME");

        log.info("Database url: {}", url);

        properties.put("hibernate.connection.url", url);
        properties.put("hibernate.connection.username", username);
        properties.put("hibernate.connection.password", password);

        return properties;
    }

    private synchronized SessionFactory getSessionFactory() {
        final LoadedConfig aggregatedCfgXml = configuration.getStandardServiceRegistryBuilder().getAggregatedCfgXml();
        final ServiceRegistry serviceRegistry =
                new StandardServiceRegistryBuilder()
                        .configure(aggregatedCfgXml)
                        .applySettings(configuration.getProperties())
                        .build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    @Override
    public synchronized void configureNewSession(Configuration configuration) {
        this.configuration = configuration;
        this.session = getSessionFactory().openSession();
    }

    @Override
    public Configuration getConfiguration() {
        return configuration;
    }

    public void close() {
        if ((session != null) && session.isOpen()) {
            session.close();
        }
    }
}
