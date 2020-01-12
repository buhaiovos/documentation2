package edu.cad.study.cycle;

import edu.cad.entities.Cycle;
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
public class CycleService implements EntityService<Cycle, Integer, CycleDto> {
    CycleRepository repo;

    @Override
    public List<Cycle> getAll() {
        return repo.findAll();
    }

    @Override
    public Optional<Cycle> findById(Integer id) {
        return repo.findById(id);
    }

    @Override
    public Cycle create(CycleDto dto) {
        var newCycle = new Cycle()
                .setDenotation(dto.getDenotation());
        return repo.save(newCycle);
    }

    @Override
    public Cycle update(Integer id, CycleDto dto) {
        var cycle = repo.findById(id).orElseThrow();
        cycle.setDenotation(dto.getDenotation());
        return cycle;
    }

    @Override
    public void deleteById(Integer id) {
        repo.deleteById(id);
    }
}
