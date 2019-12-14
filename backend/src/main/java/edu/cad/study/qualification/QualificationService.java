package edu.cad.study.qualification;

import edu.cad.entities.Qualification;
import edu.cad.study.EntityService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class QualificationService implements EntityService<Qualification, Integer, QualificationDto> {
    QualificationRepositoryWrapper repo;

    @Override
    public List<Qualification> getAll() {
        return repo.findAll();
    }

    @Override
    public Optional<Qualification> findById(Integer id) {
        return repo.findById(id);
    }

    @Override
    public Qualification create(QualificationDto qualification) {
        log.info("Creating qualification: {}", qualification);

        Qualification newQualification = new Qualification()
                .setDenotation(qualification.getDenotation());

        return repo.save(newQualification);
    }

    @Override
    public Qualification update(QualificationDto qualification) {
        Qualification existing =
                repo.findById(qualification.getId()).orElseThrow();

        existing.setDenotation(qualification.getDenotation());
        return repo.save(existing);
    }

    @Override
    public void deleteById(Integer id) {
        repo.deleteById(id);
    }
}
