package edu.cad.services.years;

import edu.cad.utils.databaseutils.DatabaseSwitcher;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class DbYearsServiceImpl implements DbYearsService {
    private final DbYearsTrackingService yearsTrackingService;
    private final DatabaseSwitcher databaseSwitcher;

    public DbYearsServiceImpl(DbYearsTrackingService yearsTrackingService, DatabaseSwitcher databaseSwitcher) {
        this.yearsTrackingService = yearsTrackingService;
        this.databaseSwitcher = databaseSwitcher;
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
