package edu.cad.utils.databaseutils;

import edu.cad.utils.hibernateutils.HibernateSessionManager;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static edu.cad.utils.hibernateutils.NativeQueryExecutor.executeQueryWithinTransaction;

public class DatabaseSwitcher {
    private static final String DATABASE_NAME_REGEX = "cad_database_([0-9]{4})";
    private static final Pattern EXTRACT_DATABASE_YEAR_PATTERN = Pattern.compile(DATABASE_NAME_REGEX);
    private static final String CAD_DATABASE = "cad_database_";
    private static final String ILLEGAL_SWITCH_MESSAGE_FORMAT = "Illegal switch: database for previous year %d should exist";
    private static final String HIBERNATE_CONNECTION_URL = "hibernate.connection.url";

    private final HibernateSessionManager sessionManager;
    private final DatabaseCloner databaseCloner;

    public DatabaseSwitcher(HibernateSessionManager sessionManager, DatabaseCloner databaseCloner) {
        this.sessionManager = sessionManager;
        this.databaseCloner = databaseCloner;
    }

    public void switchDatabaseForYear(int year) {
        if (exist(year)) {
            switchDbSessionToSelectedYear(year);
            return;
        }

        final int previousYear = year - 1;
        if (!exist(previousYear)) {
            throw new RuntimeException(String.format(ILLEGAL_SWITCH_MESSAGE_FORMAT, previousYear));
        }

        // we should switch db to year which is previous to requested year
        // in order to get session object from which we will copy data
        switchDbSessionToSelectedYear(previousYear);
        createDatabase(year);
        Session prevYearSession = switchDatabaseAndReturnPreviousSession(year);
        databaseCloner.cloneDatabase(prevYearSession);
    }

    private void switchDbSessionToSelectedYear(int year) {
        if (!isCurrent(year)) {
            Session dbSessionForPreviousActiveYear = switchDatabaseAndReturnPreviousSession(year);
            dbSessionForPreviousActiveYear.close();
        }
    }

    public int getDatabaseYear() {
        Configuration configuration = sessionManager.getConfiguration();
        String url = configuration.getProperty(HIBERNATE_CONNECTION_URL);
        return extractDatabaseYearFromConnectionUrl(url);
    }

    private int extractDatabaseYearFromConnectionUrl(String url) {
        Optional<String> databaseName = getDbNameFromConnectionUrl(url);

        return databaseName
                .map(name -> name.substring(name.length() - 4))
                .map(Integer::parseInt)
                .orElseThrow(() -> new RuntimeException("Invalid database url! Check your configuration"));
    }

    private Optional<String> getDbNameFromConnectionUrl(String url) {
        final Matcher databaseNameMatcher = EXTRACT_DATABASE_YEAR_PATTERN.matcher(url);
        return databaseNameMatcher.find() ? Optional.ofNullable(databaseNameMatcher.group())
                : Optional.empty();
    }

    private boolean exist(int year) {
        Set<Integer> years = DatabaseYears.getAllYears();
        return years.contains(year);
    }

    private boolean isCurrent(int year) {
        final Configuration configuration = sessionManager.getConfiguration();
        final String url = configuration.getProperty(HIBERNATE_CONNECTION_URL);
        final int databaseYear = extractDatabaseYearFromConnectionUrl(url);

        return year == databaseYear;
    }

    private Session switchDatabaseAndReturnPreviousSession(int year) {
        final Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
        final String oldUrl = configuration.getProperty(HIBERNATE_CONNECTION_URL);
        final String newDatabaseUrl = oldUrl.replaceFirst(DATABASE_NAME_REGEX, CAD_DATABASE + Integer.toString(year));
        configuration.setProperty(HIBERNATE_CONNECTION_URL, newDatabaseUrl);

        Session prevSession = sessionManager.getCurrentSession();
        sessionManager.configureNewSession(configuration);

        return prevSession;
    }

    private void createDatabase(int year) {
        String sql = "CREATE SCHEMA cad_database_" + year;
        executeQueryWithinTransaction(sql);
        DatabaseYears.addYearToYearsTrackingFile(year);
    }
}