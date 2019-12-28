package edu.cad.study.curriculum;

import edu.cad.entities.Curriculum;
import edu.cad.study.EntityService;
import edu.cad.study.ToDoException;
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
public class CurriculumService implements EntityService<Curriculum, Integer, CurriculumDto> {
    CurriculumRepositoryWrapper repo;

    @Override
    public List<Curriculum> getAll() {
        return repo.findAll();
    }

    @Override
    public Optional<Curriculum> findById(Integer id) {
        return repo.findById(id);
    }

    @Override
    public Curriculum create(CurriculumDto control) {
        throw new ToDoException();
    }

    @Override
    public Curriculum update(Integer id, CurriculumDto updatedGroup) {
        throw new ToDoException();
    }

    @Override
    public void deleteById(Integer integer) {
        throw new ToDoException();
    }
}
