package edu.cad.configuration;

import edu.cad.services.years.DbYearsTrackingService;
import edu.cad.utils.databaseutils.DatabaseCloner;
import edu.cad.utils.databaseutils.DatabaseSwitcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class DocumentationBeans {
    @Bean
    @Lazy
    public DatabaseSwitcher databaseSwitcher(@Lazy DbYearsTrackingService yearsTrackingService) {
        return new DatabaseSwitcher(
                new DatabaseCloner(),
                yearsTrackingService
        );
    }
}
