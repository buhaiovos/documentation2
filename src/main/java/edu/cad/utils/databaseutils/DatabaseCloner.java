package edu.cad.utils.databaseutils;

import edu.cad.daos.HibernateDao;
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

    private int newYear;

    public DatabaseCloner(HibernateSessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public void cloneDatabase(Session oldSession) {
        //getCurrentSession Class objects of all @Entity classes
        List<Class<? extends IDatabaseEntity>> entityClasses = getEntityClasses();
        // saveOldData();
        for (Class<? extends IDatabaseEntity> classObj : entityClasses) {
            entityMap.put(classObj, getAll(classObj, oldSession));
        }
        //noinspection unchecked
        oldGroups = (List<AcademicGroup>) entityMap.get(AcademicGroup.class);
        // remove academic group
        entityMap.remove(AcademicGroup.class);
        oldSession.close();

        // copy everything except groups
        executeQueryWithinTransaction(FK_CHECKS_0_QUERY);
        for (Class<? extends IDatabaseEntity> classObj : entityMap.keySet()) {
            cloneAllEntriesOfEntity(classObj, entityMap.get(classObj));
        }

        //handle groups
        rewriteGroups();
        executeQueryWithinTransaction(FK_CHECKS_1_QUERY);
    }


    private List<Class<? extends IDatabaseEntity>> getEntityClasses() {
        Set<Class<? extends IDatabaseEntity>> typesOfDatabaseEntities = new Reflections("edu.cad.entities")
                .getSubTypesOf(IDatabaseEntity.class);
        return new ArrayList<>(typesOfDatabaseEntities);
    }

    @SuppressWarnings("unchecked")
    private List<IDatabaseEntity> getAll(Class<? extends IDatabaseEntity> classObj, Session oldSession) {
        HibernateDao<IDatabaseEntity> sourceDAO = new HibernateDao(classObj, oldSession);
        return sourceDAO.getAll();
    }

    @SuppressWarnings("unchecked")
    private void cloneAllEntriesOfEntity(Class<? extends IDatabaseEntity> classObj,
                                         List<? extends IDatabaseEntity> list) {
        Session currentSession = sessionManager.getCurrentSession();
        HibernateDao<IDatabaseEntity> destDAO = new HibernateDao(classObj, currentSession);

        for (IDatabaseEntity entry : list) {
            destDAO.create(entry);
        }
    }

    private void rewriteGroups() {
        findNewYear();
        List<AcademicGroup> oldFirstYearGroups = findGroupsByStartYear(newYear - 1);
        newGroups = createNewGroups(oldFirstYearGroups);

        reassignWorkingPlans();
        removeOldestGroups();
        saveAllGroups();
    }

    private void findNewYear() {
        int highestStartYear = oldGroups.stream()
                .mapToInt(AcademicGroup::getStartYear)
                .max()
                .orElseThrow();

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
            var newGroup = new AcademicGroup();
            setCipher(newGroup, oldGroup, baseCipher);
            newGroup.setBudgetaryStudents(0);
            newGroup.setContractStudents(0);
            newGroup.setSpecialization(oldGroup.getSpecialization());
            newGroup.setQualification(oldGroup.getQualification());
            newGroup.setEducationForm(oldGroup.getEducationForm());
            newGroup.setStartYear(newYear);
            newGroup.setWorkingPlan(oldGroup.getWorkingPlan());

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

    private void reassignWorkingPlans() {
        int oldestBachelorGroupsStartYear = newYear - 4;
        int oldestMasterGroupStartYear = newYear - 2;

        for (AcademicGroup group : oldGroups) {
            if (group.getQualification().getId() == 1) { // bachelors
                if (group.getStartYear() > oldestBachelorGroupsStartYear) {
                    reassignWorkingPlanToNewGroup(group);
                }
            } else {
                if (group.getStartYear() > oldestMasterGroupStartYear) {
                    reassignWorkingPlanToNewGroup(group);
                }
            }
        }
    }

    private void reassignWorkingPlanToNewGroup(AcademicGroup group) {
        findSameOneYearOlderGroup(group)
                .ifPresent(olderGroup -> group.setWorkingPlan(olderGroup.getWorkingPlan()));
    }

    private Optional<AcademicGroup> findSameOneYearOlderGroup(AcademicGroup youngerGroup) {
        int startYearOfOlderGroup = youngerGroup.getStartYear() - 1;
        int specializationId = youngerGroup.getSpecialization().getId();
        int qualificationId = youngerGroup.getQualification().getId();
        int educationFormId = youngerGroup.getEducationForm().getId();

        for (AcademicGroup group : oldGroups) {
            if ((group.getStartYear() == startYearOfOlderGroup)
                    && (group.getSpecialization().getId() == specializationId)
                    && (group.getQualification().getId() == qualificationId)
                    && (group.getEducationForm().getId() == educationFormId)) {
                return Optional.of(group);
            }
        }
        return Optional.empty();
    }

    private void removeOldestGroups() {
        List<AcademicGroup> oldestBachGroups = findGroupsByStartYear(newYear - 4);
        oldGroups.removeAll(oldestBachGroups);

        int oldestMagStartYear = newYear - 2;
        oldGroups.removeIf(group ->
                (group.getQualification().getId() != 1) //not bachelor
                        && (group.getStartYear() == oldestMagStartYear)
        );
    }

    private void saveAllGroups() {
        var groupsDAO = new HibernateDao<>(AcademicGroup.class);

        for (AcademicGroup oldGroup : oldGroups) {
            groupsDAO.update(oldGroup);
        }
        for (AcademicGroup newGroup : newGroups) {
            groupsDAO.create(newGroup);
        }
    }
}