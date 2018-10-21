package edu.cad.configuration;

import edu.cad.services.years.DbYearsTrackingService;
import edu.cad.utils.databaseutils.DatabaseCloner;
import edu.cad.utils.databaseutils.DatabaseSwitcher;
import edu.cad.utils.hibernateutils.HibernateSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class DocumentationBeans {
    @Bean
    public DatabaseSwitcher databaseSwitcher(@Lazy DbYearsTrackingService yearsTrackingService) {
        return new DatabaseSwitcher(
                HibernateSessionManager.getInstance(),
                new DatabaseCloner(HibernateSessionManager.getInstance()),
                yearsTrackingService
        );
    }
}