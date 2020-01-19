package edu.cad.study.staff;

import edu.cad.domain.StaffType;
import edu.cad.entities.Staff;
import edu.cad.study.EntityService;
import edu.cad.study.educationform.EducationFormService;
import edu.cad.utils.k3.SourceOfFinancing;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StaffService implements EntityService<Staff, Integer, StaffDto> {
    private final StaffRepositoryWrapper repo;
    private final EducationFormService educationFormService;

    @Override
    public List<Staff> getAll() {
        return repo.findAll();
    }

    @Override
    public Optional<Staff> findById(Integer id) {
        return repo.findById(id);
    }

    @Override
    public Staff create(StaffDto staff) {
        var educationForm = educationFormService.findById(staff.getEducationFormId())
                .orElseThrow();
        var newStaff = new Staff()
                .setFirstName(staff.getFirstName())
                .setLastName(staff.getLastName())
                .setMiddleName(staff.getMiddleName())
                .setType(StaffType.valueOf(staff.getType()))
                .setEducationForm(educationForm)
                .setDegree(staff.getDegree())
                .setPosition(staff.getPosition())
                .setRate(staff.getRate())
                .setSourceOfFinancing(SourceOfFinancing.valueOf(staff.getSourceOfFinancing()));

        return repo.save(newStaff);
    }

    @Override
    public Staff update(Integer id, StaffDto staff) {
        var updated = repo.findById(id).orElseThrow();
        var educationForm = educationFormService
                .findById(staff.getEducationFormId())
                .orElseThrow();

        updated.setFirstName(staff.getFirstName())
                .setLastName(staff.getLastName())
                .setMiddleName(staff.getMiddleName())
                .setType(StaffType.valueOf(staff.getType()))
                .setEducationForm(educationForm)
                .setDegree(staff.getDegree())
                .setPosition(staff.getPosition())
                .setRate(staff.getRate())
                .setSourceOfFinancing(SourceOfFinancing.valueOf(staff.getSourceOfFinancing()));

        return updated;
    }

    @Override
    public void deleteById(Integer id) {
        repo.deleteById(id);
    }
}
