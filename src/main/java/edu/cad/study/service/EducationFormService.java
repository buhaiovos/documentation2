package edu.cad.study.service;

import edu.cad.entities.EducationForm;
import edu.cad.study.persistence.EducationFormRepository;
import edu.cad.study.web.educationform.EducationFormDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EducationFormService {
    EducationFormRepository repo;

    public List<EducationForm> getAll() {
        return repo.findAll();
    }

    public Optional<EducationForm> findById(int id) {
        return repo.findById(id);
    }

    public EducationForm create(EducationFormDto educationForm) {
        EducationForm newEducationForm =
                new EducationForm().setDenotation(educationForm.getDenotation());
        return repo.save(newEducationForm);
    }

    public EducationForm update(EducationFormDto updated) {
        EducationForm existing = findById(updated.getId()).orElseThrow();
        existing.setDenotation(updated.getDenotation());
        return repo.save(existing);
    }

    public void deleteById(int id) {
        repo.deleteById(id);
    }
}
