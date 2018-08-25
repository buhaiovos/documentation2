package edu.cad.utils.databaseutils;

import edu.cad.daos.HibernateDAO;
import edu.cad.entities.AcademicGroup;
import edu.cad.entities.interfaces.IDatabaseEntity;
import edu.cad.utils.hibernateutils.HibernateSessionManager;
import org.hibernate.Session;
import org.reflections.Reflections;

import java.util.*;

import static edu.cad.utils.hibernateutils.NativeQueryExecutor.executeQueryWithinTransaction;

public class DatabaseCloner {
    private static final String FK_CHECKS_0_QUERY = "SET FOREIGN_KEY_CHECKS=0";
    private static final String FK_CHECKS_1_QUERY = "SET FOREIGN_KEY_CHECKS=1";

    private final HibernateSessionManager sessionManager;

    private Map<Class<? extends IDatabaseEntity>, List<? extends IDatabaseEntity>> entityMap = new HashMap<>();
    private List<AcademicGroup> oldGroups = null;
    private List<AcademicGroup> newGroups = null;
    private List<Class<? extends IDatabaseEntity>> entityClasses = null;

    private int newYear;

    public DatabaseCloner(HibernateSessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public void cloneDatabase(Session oldSession) {
        //getCurrentSession Class objects of all @Entity classes
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
        executeQueryWithinTransaction(FK_CHECKS_0_QUERY);
        for (Class<? extends IDatabaseEntity> classObj : entityMap.keySet()) {
            cloneAllEntriesOfEntity(classObj, entityMap.get(classObj));
        }

        //handle groups
        rewriteGroups();
        executeQueryWithinTransaction(FK_CHECKS_1_QUERY);
    }


    private List<Class<? extends IDatabaseEntity>> getEntityClasses() {
        Set<Class<? extends IDatabaseEntity>> set =
                new Reflections("edu.cad.entities")
                        .getSubTypesOf(IDatabaseEntity.class);
        List<Class<? extends IDatabaseEntity>> list = new ArrayList<>();
        list.addAll(set);
        return list;
    }

    private List<IDatabaseEntity> fill(Class<? extends IDatabaseEntity> classObj, Session oldSession) {
        HibernateDAO<IDatabaseEntity> sourceDAO = new HibernateDAO(classObj, oldSession);
        List<IDatabaseEntity> allEntries = sourceDAO.getAll();

        return allEntries;
    }

    private void cloneAllEntriesOfEntity(Class<? extends IDatabaseEntity> classObj,
                                         List<? extends IDatabaseEntity> list) {
        Session currentSession = sessionManager.getCurrentSession();
        HibernateDAO<IDatabaseEntity> destDAO = new HibernateDAO(classObj, currentSession);

        for (IDatabaseEntity entry : list) {
            destDAO.create(entry);
        }
    }

    private void rewriteGroups() {
        findNewYear();
        List<AcademicGroup> oldFirstYearGroups = findGroupsByStartYear(newYear - 1);
        newGroups = createNewGroups(oldFirstYearGroups);

        reassignWorkplans();
        removeOldestGroups();
        saveAllGroups();
    }

    private void findNewYear() {
        int highestStartYear = -1;
        for (AcademicGroup group : oldGroups) {
            if (group.getStartYear() > highestStartYear) {
                highestStartYear = group.getStartYear();
            }
        }

        newYear = highestStartYear + 1;
    }

    private List<AcademicGroup> findGroupsByStartYear(int startYear) {
        List<AcademicGroup> groups = new ArrayList<>();
        for (AcademicGroup group : oldGroups) {
            if (group.getStartYear() == startYear) {
                groups.add(group);
            }
        }
        return groups;
    }

    private List<AcademicGroup> createNewGroups(List<AcademicGroup> oldGroups) {
        List<AcademicGroup> newGroups = new ArrayList<>();
        String baseCipher = "ДА-" + Integer.toString(newYear).charAt(3);

        createNewGroupsFromExistingOld(oldGroups, newGroups, newYear, baseCipher);

        return newGroups;
    }

    private void createNewGroupsFromExistingOld(List<AcademicGroup> oldGroups, List<AcademicGroup> newGroups,
                                                int newYear, String baseCipher) {
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

    private void setCipher(AcademicGroup newGroup, AcademicGroup oldGroup, String baseCipher) {
        if (oldGroup.getEducationForm().getId() != 2) {
            newGroup.setCipher(baseCipher + oldGroup.getCipher().substring(4));
        } else { // ДА-з32м
            char yearPart = baseCipher.charAt(3);
            baseCipher = baseCipher.substring(0, 3) + "з" + yearPart;
            newGroup.setCipher(baseCipher + oldGroup.getCipher().substring(5));
        }
    }

    private void reassignWorkplans() {
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

    private AcademicGroup findSameOneYearOlderGroup(
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

    private void removeOldestGroups() {
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

    private void saveAllGroups() {
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