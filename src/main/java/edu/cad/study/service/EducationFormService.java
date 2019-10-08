package edu.cad.study.service;

import edu.cad.entities.EducationForm;
import edu.cad.study.persistence.EducationFormRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EducationFormService {
    EducationFormRepository repo;

    public List<EducationForm> getAll() {
        return repo.findAll();
    }
}
