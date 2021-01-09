package edu.cad.study.academicgroup;

import edu.cad.entities.*;
import edu.cad.study.EntityService;
import edu.cad.study.educationform.EducationFormService;
import edu.cad.study.qualification.QualificationService;
import edu.cad.study.specialization.SpecializationService;
import edu.cad.study.workingplan.WorkingPlanService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AcademicGroupService implements EntityService<AcademicGroup, Integer, AcademicGroupDto> {
    AcademicGroupRepositoryWrapper repo;

    SpecializationService specializations;
    EducationFormService educationForms;
    QualificationService qualifications;
    WorkingPlanService workingPlans;

    @Override
    public List<AcademicGroup> getAll() {
        return repo.findAll();
    }

    public List<AcademicGroup> findAllByIds(List<Integer> ids) {
        return repo.findAllByIds(ids);
    }

    @Override
    public Optional<AcademicGroup> findById(Integer id) {
        return repo.findById(id);
    }

    @Override
    @Transactional
    public AcademicGroup create(AcademicGroupDto academicGroup) {
        log.info("Creating academic group: {}", academicGroup);

        Specialization specialization = specializations
                .findById(academicGroup.getSpecialization().id())
                .orElseThrow();
        EducationForm educationForm = educationForms
                .findById(academicGroup.getEducationForm().id())
                .orElseThrow();
        Qualification qualification = qualifications
                .findById(academicGroup.getQualification().id())
                .orElseThrow();
        WorkingPlan workingPlan = workingPlans
                .findById(academicGroup.getWorkingPlan().id())
                .orElseThrow();

        AcademicGroup newGroup = new AcademicGroup()
                .setBudgetaryStudents(academicGroup.getBudgetaryStudents())
                .setContractStudents(academicGroup.getContractStudents())
                .setCipher(academicGroup.getCipher())
                .setStartYear(academicGroup.getStartYear())
                .setQualification(qualification)
                .setEducationForm(educationForm)
                .setSpecialization(specialization)
                .setWorkingPlan(workingPlan);
        log.info("Saving academic group: {}", academicGroup);
        return repo.save(newGroup);
    }

    @Override
    @Transactional
    public AcademicGroup update(Integer id, AcademicGroupDto updatedGroup) {
        log.info("Updating academic group: {}", updatedGroup);

        final AcademicGroup existingGroup = findById(id).orElseThrow();
        log.info("Found target group: {}", existingGroup);

        final Specialization specialization = specializations
                .findById(updatedGroup.getSpecialization().id())
                .orElseThrow();
        final EducationForm educationForm = educationForms
                .findById(updatedGroup.getEducationForm().id())
                .orElseThrow();
        final Qualification qualification = qualifications
                .findById(updatedGroup.getQualification().id())
                .orElseThrow();
        final WorkingPlan workingPlan = workingPlans
                .findById(updatedGroup.getWorkingPlan().id())
                .orElseThrow();


        existingGroup
                .setBudgetaryStudents(updatedGroup.getBudgetaryStudents())
                .setContractStudents(updatedGroup.getContractStudents())
                .setCipher(updatedGroup.getCipher())
                .setStartYear(updatedGroup.getStartYear())
                .setQualification(qualification)
                .setEducationForm(educationForm)
                .setSpecialization(specialization)
                .setWorkingPlan(workingPlan);

        log.info("Saving updated group: {}", existingGroup);
        return repo.save(existingGroup);
    }

    @Override
    public void deleteById(Integer id) {
        repo.deleteById(id);
    }
}
