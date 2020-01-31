package edu.cad.study.worktype;

import edu.cad.entities.WorkType;
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
public class WorkTypeService implements EntityService<WorkType, Integer, WorkTypeDto> {
    WorkTypeRepositoryWrapper repo;

    @Override
    public List<WorkType> getAll() {
        return repo.findAll();
    }

    @Override
    public Optional<WorkType> findById(Integer id) {
        return repo.findById(id);
    }

    @Override
    public WorkType create(WorkTypeDto workTypeDto) {
        return repo.save(
                new WorkType().setDenotation(workTypeDto.getDenotation())
        );
    }

    @Override
    public WorkType update(Integer id, WorkTypeDto workTypeDto) {
        WorkType updated = repo.findById(id).orElseThrow();
        updated.setDenotation(workTypeDto.getDenotation());
        return updated;
    }

    @Override
    public void deleteById(Integer id) {
        repo.deleteById(id);
    }
}
