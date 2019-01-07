package edu.cad.configuration;

import edu.cad.daos.HibernateDao;
import edu.cad.entities.*;
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

    @Bean
    public HibernateDao<SubjectStudyLoadDistributed> subjectStudyLoadSubmittedHibernateDao() {
        return new HibernateDao<>(SubjectStudyLoadDistributed.class);
    }

    @Bean
    public HibernateDao<SubjectStudyLoad> subjectStudyLoadHibernateDao() {
        return new HibernateDao<>(SubjectStudyLoad.class);
    }

    @Bean
    public HibernateDao<Staff> staffHibernateDao() {
        return new HibernateDao<>(Staff.class);
    }

    @Bean
    public HibernateDao<OtherLoadInfo> otherLoadInfoHibernateDao() {
        return new HibernateDao<>(OtherLoadInfo.class);
    }

    @Bean
    public HibernateDao<OtherLoad> otherLoadHibernateDao() {
        return new HibernateDao<>(OtherLoad.class);
    }

    @Bean
    public HibernateDao<OtherLoadInfoDistributed> otherLoadInfoSubmittedHibernateDao() {
        return new HibernateDao<>(OtherLoadInfoDistributed.class);
    }
}
