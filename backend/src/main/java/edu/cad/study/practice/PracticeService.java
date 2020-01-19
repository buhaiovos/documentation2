package edu.cad.study.practice;

import edu.cad.domain.PracticeType;
import edu.cad.entities.Practice;
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
public class PracticeService implements EntityService<Practice, Integer, PracticeDto> {
    PracticeRepositoryWrapper repo;

    @Override
    public List<Practice> getAll() {
        return repo.findAll();
    }

    @Override
    public Optional<Practice> findById(Integer id) {
        return repo.findById(id);
    }

    @Override
    public Practice create(PracticeDto practice) {
        final var newPractice = new Practice()
                .setSemester(practice.getSemester())
                .setWeeks(practice.getWeeks())
                .setType(PracticeType.fromDenotation(practice.getType()))
                .setStart(practice.getStart())
                .setFinish(practice.getFinish());

        return repo.save(newPractice);
    }

    @Override
    public Practice update(Integer id, PracticeDto practice) {
        Practice updated = repo.findById(id).orElseThrow();
        updated.setSemester(practice.getSemester())
                .setWeeks(practice.getWeeks())
                .setType(PracticeType.fromDenotation(practice.getType()))
                .setStart(practice.getStart())
                .setFinish(practice.getFinish());

        return updated;
    }

    @Override
    public void deleteById(Integer id) {
        repo.deleteById(id);
    }
}
