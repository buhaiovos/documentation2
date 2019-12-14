package edu.cad.study.educationform;

import edu.cad.entities.EducationForm;
import edu.cad.study.EntityService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EducationFormService implements EntityService<EducationForm, Integer, EducationFormDto> {
    EducationFormRepositoryWrapper repo;

    @Override
    public List<EducationForm> getAll() {
        return repo.findAll();
    }

    @Override
    public Optional<EducationForm> findById(Integer id) {
        return repo.findById(id);
    }

    @Override
    public EducationForm create(EducationFormDto educationForm) {
        var newEducationForm = new EducationForm()
                .setDenotation(educationForm.getDenotation());
        return repo.save(newEducationForm);
    }

    @Override
    public EducationForm update(EducationFormDto updated) {
        EducationForm existing = findById(updated.getId()).orElseThrow();
        existing.setDenotation(updated.getDenotation());
        return repo.save(existing);
    }

    @Override
    public void deleteById(Integer id) {
        repo.deleteById(id);
    }
}
