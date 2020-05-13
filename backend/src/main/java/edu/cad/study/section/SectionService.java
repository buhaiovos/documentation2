package edu.cad.study.section;

import edu.cad.entities.Section;
import edu.cad.study.EntityService;
import edu.cad.study.cycle.CycleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static edu.cad.utils.Utils.nullOr;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SectionService implements EntityService<Section, Integer, SectionDto> {
    SectionRepositoryWrapper repo;
    CycleService cycleService;

    @Override
    public List<Section> getAll() {
        return repo.findAll();
    }

    @Override
    public Optional<Section> findById(Integer id) {
        return repo.findById(id);
    }

    @Override
    public Section create(SectionDto section) {
        var newSection = new Section()
                .setDenotation(section.denotation())
                .setOptional(section.optional())
                .setCycle(nullOr(section.cycle().id(), id -> cycleService.findById(id).orElseThrow()));

        return repo.save(newSection);
    }

    @Override
    public Section update(Integer id, SectionDto section) {
        Section updated = repo.findById(id).orElseThrow();

        updated.setDenotation(section.denotation())
                .setOptional(section.optional())
                .setCycle(nullOr(section.cycle().id(), _id -> cycleService.findById(_id).orElseThrow()));

        return updated;
    }

    @Override
    public void deleteById(Integer id) {
        repo.deleteById(id);
    }
}
