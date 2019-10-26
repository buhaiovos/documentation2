package edu.cad.services.years;

import edu.cad.documentelements.areas.k3.K3ScienceResearchIndividualsArea;
import edu.cad.utils.databaseutils.DatabaseSwitcher;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Set;

@Service
public class DbYearsServiceImpl implements DbYearsService {
    private final DbYearsTrackingService yearsTrackingService;
    private final DatabaseSwitcher databaseSwitcher;

    public DbYearsServiceImpl(DbYearsTrackingService yearsTrackingService, DatabaseSwitcher databaseSwitcher) {
        this.yearsTrackingService = yearsTrackingService;
        this.databaseSwitcher = databaseSwitcher;
    }

    @PostConstruct
    public void injectMyself() {
        K3ScienceResearchIndividualsArea.setDbYearsService(this);
    }

    @Override
    public Set<Integer> getAll() {
        return yearsTrackingService.getAll();
    }

    @Override
    public int getCurrent() {
        return databaseSwitcher.getDatabaseYear();
    }

    @Override
    public void switchToYear(int year) {
        databaseSwitcher.switchDatabaseForYear(year);
        yearsTrackingService.registerNewYear(year);
    }
}
