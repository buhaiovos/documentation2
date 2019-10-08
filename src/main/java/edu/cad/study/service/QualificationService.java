package edu.cad.study.service;

import edu.cad.entities.Qualification;
import edu.cad.study.persistence.QualificationRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class QualificationService {
    QualificationRepository repo;

    public List<Qualification> getAll() {
        return repo.findAll();
    }
}
