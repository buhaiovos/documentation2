package edu.cad.study.subject.type;

import edu.cad.entities.SubjectType;
import edu.cad.study.EntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubjectTypeService implements EntityService<SubjectType, Integer, SubjectTypeDto> {
    private final SubjectTypeRepositoryWrapper repo;

    @Override
    public List<SubjectType> getAll() {
        return repo.findAll();
    }

    @Override
    public Optional<SubjectType> findById(Integer id) {
        return repo.findById(id);
    }

    @Override
    public SubjectType create(SubjectTypeDto subjectTypeDto) {
        var newSubjectType = new SubjectType();
        newSubjectType.setDenotation(subjectTypeDto.getDenotation());
        return repo.save(newSubjectType);
    }

    @Override
    public SubjectType update(final Integer id, final SubjectTypeDto subjectTypeDto) {
        SubjectType updated = repo.findById(id).orElseThrow();
        updated.setDenotation(subjectTypeDto.getDenotation());
        return updated;
    }

    @Override
    public void deleteById(Integer id) {
        repo.deleteById(id);
    }
}
