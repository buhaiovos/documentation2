package edu.cad.utils.hibernateutils;

import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

public interface HibernateSessionManager {
    Session getCurrentSession();

    Configuration getConfiguration();

    void configureNewSession(Configuration configuration);

    static HibernateSessionManager getInstance() {
        return HibernateSessionManagerImpl.INSTANCE;
    }
}
