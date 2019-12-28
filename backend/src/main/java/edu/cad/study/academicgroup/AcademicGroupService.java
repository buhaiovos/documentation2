package edu.cad.study.academicgroup;

import edu.cad.entities.*;
import edu.cad.study.EntityService;
import edu.cad.study.educationform.EducationFormService;
import edu.cad.study.qualification.QualificationService;
import edu.cad.study.specialization.SpecializationService;
import edu.cad.study.specialization.WorkingPlanService;
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

    WorkingPlanService workingPlanService;
    SpecializationService specializationService;
    EducationFormService educationFormService;
    QualificationService qualificationService;

    @Override
    public List<AcademicGroup> getAll() {
        return repo.findAll();
    }

    @Override
    public Optional<AcademicGroup> findById(Integer id) {
        return repo.findById(id);
    }

    @Override
    @Transactional
    public AcademicGroup create(AcademicGroupDto academicGroup) {
        log.info("Creating academic group: {}", academicGroup);

        WorkingPlan workingPlan = workingPlanService
                .findById(academicGroup.getWorkplan())
                .orElseThrow();
        Specialization specialization = specializationService
                .findById(academicGroup.getSpecialization())
                .orElseThrow();
        EducationForm educationForm = educationFormService
                .findById(academicGroup.getEducationForm())
                .orElseThrow();
        Qualification qualification = qualificationService
                .findById(academicGroup.getQualification())
                .orElseThrow();

        AcademicGroup newGroup = new AcademicGroup()
                .setBudgetaryStudents(academicGroup.getBudgetaryStudents())
                .setContractStudents(academicGroup.getContractStudents())
                .setCipher(academicGroup.getCipher())
                .setStartYear(academicGroup.getStartYear())
                .setQualification(qualification)
                .setEducationForm(educationForm)
                .setWorkingPlan(workingPlan)
                .setSpecialization(specialization);
        log.info("Saving academic group: {}", academicGroup);
        return repo.save(newGroup);
    }

    @Override
    @Transactional
    public AcademicGroup update(Integer id, AcademicGroupDto updatedGroup) {
        log.info("Updating academic group: {}", updatedGroup);

        final AcademicGroup existingGroup = findById(id).orElseThrow();
        log.info("Found target group: {}", existingGroup);

        final WorkingPlan workingPlan = workingPlanService
                .findById(updatedGroup.getWorkplan())
                .orElseThrow();
        final Specialization specialization = specializationService
                .findById(updatedGroup.getSpecialization())
                .orElseThrow();
        final EducationForm educationForm = educationFormService
                .findById(updatedGroup.getEducationForm())
                .orElseThrow();
        final Qualification qualification = qualificationService
                .findById(updatedGroup.getQualification())
                .orElseThrow();

        existingGroup
                .setBudgetaryStudents(updatedGroup.getBudgetaryStudents())
                .setContractStudents(updatedGroup.getContractStudents())
                .setCipher(updatedGroup.getCipher())
                .setStartYear(updatedGroup.getStartYear())
                .setQualification(qualification)
                .setEducationForm(educationForm)
                .setWorkingPlan(workingPlan)
                .setSpecialization(specialization);

        log.info("Saving updated group: {}", existingGroup);
        return repo.save(existingGroup);
    }

    @Override
    public void deleteById(Integer id) {
        repo.deleteById(id);
    }
}
