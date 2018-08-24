package edu.cad.utils.databaseutils;

import edu.cad.utils.hibernateutils.HibernateSessionHolder;
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

    private DatabaseSwitcher() {
        // no instances of utility classes
    }

    public static void switchDatabase(int year) {
        if (exist(year)) {
            switchDatabaseAndReturnPreviousSession(year).close();
            return;
        }

        final int previousYear = year - 1;
        if (!exist(previousYear)) {
            throw new RuntimeException(String.format(ILLEGAL_SWITCH_MESSAGE_FORMAT, previousYear));
        }

        // we should switch db to year which is previous to requested year
        // in order to get session object from which we will copy data
        switchDatabaseAndReturnPreviousSession(previousYear).close();
        createDatabase(year);
        Session prevYearSession = switchDatabaseAndReturnPreviousSession(year);
        DatabaseCloner.cloneDatabase(prevYearSession);
    }

    public static void dropDatabase(int year) {
        if (exist(year)) {
            if (isCurrent(year)) {
                HibernateSessionHolder.INSTANCE.close();
            }

            String sql = "DROP DATABASE cad_database_" + year;
            executeQueryWithinTransaction(sql);

            DatabaseYears.deleteYear(year);
        }
    }

    public static int getDatabaseYear() {
        Configuration configuration = HibernateSessionHolder.INSTANCE.getConfiguration();
        if (configuration == null)
            return -1;
        String url = configuration.getProperty(HIBERNATE_CONNECTION_URL);

        return extractDatabaseYearFromConnectionUrl(url);
    }

    private static int extractDatabaseYearFromConnectionUrl(String url) {
        final Matcher databaseNameMatcher = EXTRACT_DATABASE_YEAR_PATTERN.matcher(url);
        Optional<String> databaseName = databaseNameMatcher.find() ? Optional.ofNullable(databaseNameMatcher.group())
                : Optional.empty();
        Optional<String> databaseYear = databaseName.map(name -> name.substring(name.length() - 4));

        return Integer.valueOf(
                databaseYear.orElseThrow(() -> new RuntimeException("Invalid database url! Check your configuration"))
        );
    }

    private static boolean exist(int year) {
        Set<Integer> years = DatabaseYears.getAllYears();
        return years.contains(year);
    }

    private static boolean isCurrent(int year) {
        final Configuration configuration = HibernateSessionHolder.INSTANCE.getConfiguration();
        final String url = configuration.getProperty(HIBERNATE_CONNECTION_URL);
        final int databaseYear = extractDatabaseYearFromConnectionUrl(url);

        return year == databaseYear;
    }

    private static Session switchDatabaseAndReturnPreviousSession(int year) {
        final Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
        final String oldUrl = configuration.getProperty(HIBERNATE_CONNECTION_URL);
        final String newDatabaseUrl = oldUrl.replaceFirst(DATABASE_NAME_REGEX, CAD_DATABASE + Integer.toString(year));
        configuration.setProperty(HIBERNATE_CONNECTION_URL, newDatabaseUrl);

        Session prevSession = HibernateSessionHolder.INSTANCE.getSession();
        HibernateSessionHolder.INSTANCE.openNewConfiguredSession(configuration);

        return prevSession;
    }

    private static void createDatabase(int year) {
        String sql = "CREATE SCHEMA cad_database_" + year;
        executeQueryWithinTransaction(sql);
        DatabaseYears.addYearToYearsTrackingFile(year);
    }
}