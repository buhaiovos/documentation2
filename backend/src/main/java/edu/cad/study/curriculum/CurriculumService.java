package edu.cad.study.curriculum;

import edu.cad.entities.Curriculum;
import edu.cad.entities.CurriculumSubject;
import edu.cad.study.EntityService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CurriculumService implements EntityService<Curriculum, Integer, CurriculumDto> {
    CurriculumSubjectService curriculumSubjectService;
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
    public Curriculum create(CurriculumDto curriculumDto) {
        var curriculum = new Curriculum().setDenotation(curriculumDto.getDenotation());
        final var newCurriculum = repo.save(curriculum);

        Set<CurriculumSubject> curriculumSubjects =
                curriculumSubjectService.getCurriculumSubjects(curriculumDto.getSubjectIdsByCiphers(), newCurriculum);

        newCurriculum.setCurriculumSubjects(curriculumSubjects);
        return repo.save(newCurriculum);
    }

    @Override
    public Curriculum update(Integer id, CurriculumDto curriculumDto) {
        var curriculum = repo.findById(id).orElseThrow();
        curriculum.setDenotation(curriculumDto.getDenotation());

        Set<CurriculumSubject> curriculumSubjects =
                curriculumSubjectService.getCurriculumSubjects(curriculumDto.getSubjectIdsByCiphers(), curriculum);

        curriculum.setCurriculumSubjects(curriculumSubjects);

        return curriculum;
    }

    @Override
    public void deleteById(Integer id) {
        repo.deleteById(id);
    }
}
