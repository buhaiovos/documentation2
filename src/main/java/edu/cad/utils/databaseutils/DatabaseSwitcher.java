package edu.cad.utils.databaseutils;

import edu.cad.utils.Utils;
import edu.cad.utils.hibernateutils.HibernateSession;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DatabaseSwitcher {

    private static final String DATABASE_NAME_REGEX = "cad_database_([0-9]{4})";
    private static final Pattern EXTRACT_DATABASE_YEAR_PATTERN = Pattern.compile(DATABASE_NAME_REGEX);
    private static final String CAD_DATABASE = "cad_database_";

    public static boolean switchDatabase(int year){
        if(exist(year)){
            switchDatabaseAndSession(year).close();
            return true;
        }

        if(!exist(year - 1)){
            return false;
        }

        switchDatabaseAndSession(year - 1).close();
        Session prevYearSession = HibernateSession.getInstance();
        createDatabase(year);
        switchDatabaseAndSession(year);
        DatabaseCloner.cloneDatabase(prevYearSession);

        return true;
    }

    public static void dropDatabase(int year){
        if(exist(year)){
            if(isCurrent(year)){
                HibernateSession.close();
            }

            String sql = "DROP DATABASE cad_database_" + year;
            executeSQLQuery(sql);

            DatabaseYears.deleteYear(year);
        }
    }

    public static int getDatabaseYear(){
        Configuration configuration = HibernateSession.getConfiguration();
        if(configuration == null)
            return -1;

        String url = configuration.getProperty("hibernate.connection.url");
        Matcher databaseNameMatcher = EXTRACT_DATABASE_YEAR_PATTERN.matcher(url);

        Optional<String> databaseName = databaseNameMatcher.find() ? Optional.ofNullable(databaseNameMatcher.group())
                : Optional.empty();

        Optional<String> databaseYear = databaseName.map(name -> name.substring(name.length() - 4));

        return Integer.valueOf(databaseYear.orElse("-1"));
    }

    private static boolean exist(int year){
        Set<Integer> years = DatabaseYears.getAllYears();

        for(int element : years){
            if(element == year){
                return true;
            }
        }

        return false;
    }

    private static boolean isCurrent(int year){
        Configuration configuration = HibernateSession.getConfiguration();

        if(configuration == null)
            return false;

        String url = configuration.getProperty("hibernate.connection.url");
        int yearPosition = url.lastIndexOf("_");

        String yearString = url.substring(yearPosition + 1, url.length());
        if(Utils.isParseable(yearString)){
            if(Integer.parseInt(yearString) == year){
                return true;
            }
        }

        return false;
    }

    private static Session switchDatabaseAndSession(int year){
        Configuration configuration = new Configuration().configure("hibernate.cfg.xml");

        String oldUrl = configuration.getProperty("hibernate.connection.url");
        String newDatabaseUrl = oldUrl.replaceFirst(DATABASE_NAME_REGEX, CAD_DATABASE + Integer.toString(year));

        configuration.setProperty("hibernate.connection.url", newDatabaseUrl);

        DatabaseYears.addYear(year);

        Session prevSession = HibernateSession.getInstance();
        HibernateSession.openSession(configuration);

        return prevSession;
    }

    private static void createDatabase(int year){
        String sql = "CREATE SCHEMA cad_database_" + year;
        executeSQLQuery(sql);
    }

    private static void executeSQLQuery(String sql){
        SQLQuery query = HibernateSession.getInstance().createSQLQuery(sql);
        query.executeUpdate();
    }
}