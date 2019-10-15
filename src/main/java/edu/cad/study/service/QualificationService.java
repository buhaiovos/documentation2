package edu.cad.study.service;

import edu.cad.entities.Qualification;
import edu.cad.study.persistence.QualificationRepositoryWrapper;
import edu.cad.study.web.qualification.QualificationDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class QualificationService implements EntityService<Qualification, Integer, QualificationDto> {
    QualificationRepositoryWrapper repo;

    @Override
    public List<Qualification> getAll() {
        return null;
    }

    @Override
    public Optional<Qualification> findById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public Qualification create(QualificationDto academicGroup) {
        return null;
    }

    @Override
    public Qualification update(QualificationDto updatedGroup) {
        return null;
    }

    @Override
    public void deleteById(Integer integer) {

    }
}
