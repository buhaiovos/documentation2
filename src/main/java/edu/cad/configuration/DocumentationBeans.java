package edu.cad.configuration;

import edu.cad.utils.databaseutils.DatabaseCloner;
import edu.cad.utils.databaseutils.DatabaseSwitcher;
import edu.cad.utils.hibernateutils.HibernateSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DocumentationBeans {
    @Bean
    public DatabaseSwitcher databaseSwitcher() {
        return new DatabaseSwitcher(
                HibernateSessionManager.getInstance(),
                new DatabaseCloner(HibernateSessionManager.getInstance())
        );
    }
}