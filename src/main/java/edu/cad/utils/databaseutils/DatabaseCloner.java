package edu.cad.utils.databaseutils;

import edu.cad.daos.HibernateDAO;
import edu.cad.entities.AcademicGroup;
import edu.cad.entities.interfaces.IDatabaseEntity;
import edu.cad.utils.hibernateutils.HibernateSession;
import org.hibernate.Session;
import org.reflections.Reflections;

import java.util.*;

public class DatabaseCloner {
    private static final String FK_CHECKS_0_QUERY = "SET FOREIGN_KEY_CHECKS=0";
    private static final String FK_CHECKS_1_QUERY = "SET FOREIGN_KEY_CHECKS=1";

    private static Map<Class<? extends IDatabaseEntity>, List<? extends IDatabaseEntity>> entityMap = new HashMap<>();
    private static List<AcademicGroup> oldGroups = null;
    private static List<AcademicGroup> newGroups = null;
    private static List<Class<? extends IDatabaseEntity>> entityClasses = null;
    private static int newYear;

    public static void cloneDatabase(Session oldSession) {
        //get Class objects of all @Entity classes
        entityClasses = getEntityClasses();
        // saveOldData();
        for (Class<? extends IDatabaseEntity> classObj : entityClasses) {
            entityMap.put(classObj, fill(classObj, oldSession));
        }
        oldGroups = (List<AcademicGroup>) entityMap.get(AcademicGroup.class);
        // remove academic group
        entityMap.remove(AcademicGroup.class);
        oldSession.close();

        // copy all except groups
        executeQuery(FK_CHECKS_0_QUERY);
        for (Class<? extends IDatabaseEntity> classObj : entityMap.keySet()) {
            cloneAllEntriesOfEntity(classObj, entityMap.get(classObj));
        }

        //handle groups
        rewriteGroups();
        executeQuery(FK_CHECKS_1_QUERY);
    }

    private static void executeQuery(String query) {
        HibernateSession.getInstance()
                .createSQLQuery(query).executeUpdate();
    }

    private static List<Class<? extends IDatabaseEntity>> getEntityClasses() {
        /*EntityManagerFactory emf =
        Persistence.createEntityManagerFactory("documentation");
        //get all entity types of @entity annotated classes
        Set<EntityType<?>> entities = emf.getMetamodel().getEntities();
        //get all 
        List<Class<? extends IDatabaseEntity>> classes = new ArrayList<>();
        for (EntityType<?> e : entities) {
            classes.add((Class<? extends IDatabaseEntity>)e.getJavaType());
        }

        return classes;*/
        Set<Class<? extends IDatabaseEntity>> set =
                new Reflections("edu.cad.entities")
                        .getSubTypesOf(IDatabaseEntity.class);
        List<Class<? extends IDatabaseEntity>> list = new ArrayList<>();
        list.addAll(set);
        return list;
    }

    private static List<IDatabaseEntity> fill(Class<? extends IDatabaseEntity> classObj, Session oldSession) {
        HibernateDAO<IDatabaseEntity> sourceDAO =new HibernateDAO(classObj, oldSession);
        List<IDatabaseEntity> allEntries = sourceDAO.getAll();

        return allEntries;
    }

    private static void cloneAllEntriesOfEntity(Class<? extends IDatabaseEntity> classObj,
                                                List<? extends IDatabaseEntity> list) {
        Session currentSession = HibernateSession.getInstance();
        HibernateDAO<IDatabaseEntity> destDAO = new HibernateDAO(classObj, currentSession);

        for (IDatabaseEntity entry : list) {
            destDAO.create(entry);
        }
    }

    private static void rewriteGroups() {
        findNewYear();
        List<AcademicGroup> oldFirstYearGroups = findGroupsByStartYear(newYear - 1);
        newGroups = createNewGroups(oldFirstYearGroups);

        reassignWorkplans();
        removeOldestGroups();
        saveAllGroups();
    }

    private static void findNewYear() {
        int highestStartYear = -1;
        for (AcademicGroup group : oldGroups) {
            if (group.getStartYear() > highestStartYear) {
                highestStartYear = group.getStartYear();
            }
        }

        newYear = highestStartYear + 1;
    }

    private static List<AcademicGroup> findGroupsByStartYear(int startYear) {
        List<AcademicGroup> groups = new ArrayList<>();
        for (AcademicGroup group : oldGroups) {
            if (group.getStartYear() == startYear) {
                groups.add(group);
            }
        }
        return groups;
    }

    private static List<AcademicGroup> createNewGroups(List<AcademicGroup> oldGroups) {
        List<AcademicGroup> newGroups = new ArrayList<>();
        String baseCipher = "ДА-" + Integer.toString(newYear).charAt(3);

        createNewGroupsFromExistingOld(oldGroups, newGroups, newYear, baseCipher);

        return newGroups;
    }

    private static void createNewGroupsFromExistingOld(List<AcademicGroup> oldGroups,
                                                       List<AcademicGroup> newGroups, int newYear, String baseCipher) {
        for (AcademicGroup oldGroup : oldGroups) {
            AcademicGroup newGroup = new AcademicGroup();
            setCipher(newGroup, oldGroup, baseCipher);
            newGroup.setBudgetaryStudents(0);
            newGroup.setContractStudents(0);
            newGroup.setSpecialization(oldGroup.getSpecialization());
            newGroup.setQualification(oldGroup.getQualification());
            newGroup.setEducationForm(oldGroup.getEducationForm());
            newGroup.setStartYear(newYear);
            newGroup.setWorkplan(oldGroup.getWorkplan());

            newGroups.add(newGroup);
        }
    }

    private static void setCipher(AcademicGroup newGroup,
                                  AcademicGroup oldGroup, String baseCipher) {
        if (oldGroup.getEducationForm().getId() != 2) {
            newGroup.setCipher(baseCipher + oldGroup.getCipher().substring(4));
        } else { // ДА-з32м
            char yearPart = baseCipher.charAt(3);
            baseCipher = baseCipher.substring(0, 3) + "з" + yearPart;
            newGroup.setCipher(baseCipher + oldGroup.getCipher().substring(5));
        }
    }

    private static void reassignWorkplans() {
        //
        int oldestBachelorGroupsStartYear = newYear - 4;
        int oldestMagGroupStartYear = newYear - 2;

        for (AcademicGroup group : oldGroups) {
            if (group.getQualification().getId() == 1) { // bachelors
                if (group.getStartYear() > oldestBachelorGroupsStartYear) {
                    AcademicGroup olderGroup = findSameOneYearOlderGroup(group);
                    if (olderGroup != null)
                        group.setWorkplan(olderGroup.getWorkplan());
                }
            } else {
                if (group.getStartYear() > oldestMagGroupStartYear) {
                    AcademicGroup olderGroup = findSameOneYearOlderGroup(group);
                    if (olderGroup != null)
                        group.setWorkplan(olderGroup.getWorkplan());
                }
            }
        }
    }

    private static AcademicGroup findSameOneYearOlderGroup(
            AcademicGroup youngerGroup) {
        int neededStartYear = youngerGroup.getStartYear() - 1;
        int neededSpecId = youngerGroup.getSpecialization().getId();
        int neededQualId = youngerGroup.getQualification().getId();
        int neededEFId = youngerGroup.getEducationForm().getId();

        for (AcademicGroup group : oldGroups) {
            if ((group.getStartYear() == neededStartYear)
                    && (group.getSpecialization().getId() == neededSpecId)
                    && (group.getQualification().getId() == neededQualId)
                    && (group.getEducationForm().getId() == neededEFId)) {
                return group;
            }
        }
        return null;
    }

    private static void removeOldestGroups() {
        //simple part
        List<AcademicGroup> oldestBachGroups = findGroupsByStartYear(newYear - 4);
        oldGroups.removeAll(oldestBachGroups);

        //bit more complex
        int oldestMagStartYear = newYear - 2;
        Iterator<AcademicGroup> iterator = oldGroups.iterator();
        while (iterator.hasNext()) {
            AcademicGroup group = iterator.next();
            if ((group.getQualification().getId() != 1) //not bachelor
                    && (group.getStartYear() == oldestMagStartYear)) {
                iterator.remove();
            }
        }
    }

    private static void saveAllGroups() {
        HibernateDAO<AcademicGroup> groupsDAO =
                new HibernateDAO<>(AcademicGroup.class);

        for (AcademicGroup oldGroup : oldGroups) {
            groupsDAO.update(oldGroup);
        }
        for (AcademicGroup newGroup : newGroups) {
            groupsDAO.create(newGroup);
        }
    }
}