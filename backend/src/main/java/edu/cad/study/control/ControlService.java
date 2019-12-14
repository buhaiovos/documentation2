package edu.cad.study.control;

import edu.cad.entities.Control;
import edu.cad.study.EntityService;
import edu.cad.study.ToDoException;
import edu.cad.study.persistence.ControlRepositoryWrapper;
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
public class ControlService implements EntityService<Control, Integer, ControlDto> {
    ControlRepositoryWrapper repo;

    @Override
    public List<Control> getAll() {
        return repo.findAll();
    }

    @Override
    public Optional<Control> findById(Integer id) {
        return repo.findById(id);
    }

    @Override
    public Control create(ControlDto control) {
        throw new ToDoException();
    }

    @Override
    public Control update(ControlDto updatedGroup) {
        throw new ToDoException();
    }

    @Override
    public void deleteById(Integer integer) {
        throw new ToDoException();
    }
}
