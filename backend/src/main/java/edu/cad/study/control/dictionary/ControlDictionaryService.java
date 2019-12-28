package edu.cad.study.control.dictionary;

import edu.cad.entities.ControlDictionary;
import edu.cad.study.EntityService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ControlDictionaryService implements EntityService<ControlDictionary, Integer, ControlDictionaryDto> {
    ControlDictionaryRepositoryWrapper repo;

    @Override
    public List<ControlDictionary> getAll() {
        return repo.findAll();
    }

    @Override
    public Optional<ControlDictionary> findById(Integer id) {
        return repo.findById(id);
    }

    @Override
    public ControlDictionary create(ControlDictionaryDto controlDictionary) {
        final var newControlDictionary = new ControlDictionary()
                .setDenotation(controlDictionary.getDenotation());
        return repo.save(newControlDictionary);
    }

    @Override
    public ControlDictionary update(Integer id, ControlDictionaryDto controlDictionary) {
        ControlDictionary updated = repo.findById(id).orElseThrow();
        updated.setDenotation(controlDictionary.getDenotation());
        return updated;
    }

    @Override
    public void deleteById(Integer id) {
        repo.deleteById(id);
    }
}
