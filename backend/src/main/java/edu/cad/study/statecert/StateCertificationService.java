package edu.cad.study.statecert;

import edu.cad.entities.StateCertification;
import edu.cad.study.EntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StateCertificationService implements EntityService<StateCertification, Integer, StateCertificationDto> {
    private final StateCertificationRepositoryWrapper repo;

    @Override
    public List<StateCertification> getAll() {
        return repo.findAll();
    }

    @Override
    public Optional<StateCertification> findById(Integer id) {
        return repo.findById(id);
    }

    @Override
    public StateCertification create(StateCertificationDto stateCertificationDto) {
        var newCertification = new StateCertification()
                .setForm(stateCertificationDto.getForm())
                .setSemester(stateCertificationDto.getSemester())
                .setStart(stateCertificationDto.getStart())
                .setFinish(stateCertificationDto.getFinish());
        return repo.save(newCertification);
    }

    @Override
    public StateCertification update(Integer id, StateCertificationDto stateCertificationDto) {
        var updated = repo.findById(id).orElseThrow();
        updated.setForm(stateCertificationDto.getForm())
                .setSemester(stateCertificationDto.getSemester())
                .setStart(stateCertificationDto.getStart())
                .setFinish(stateCertificationDto.getFinish());
        return updated;
    }

    @Override
    public void deleteById(Integer id) {
        repo.deleteById(id);
    }
}
