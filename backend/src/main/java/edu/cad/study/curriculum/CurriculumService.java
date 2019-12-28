package edu.cad.study.curriculum;

import edu.cad.entities.Curriculum;
import edu.cad.entities.CurriculumSubject;
import edu.cad.entities.SubjectInfo;
import edu.cad.study.EntityService;
import edu.cad.study.subject.info.SubjectInfoService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CurriculumService implements EntityService<Curriculum, Integer, CurriculumDto> {
    CurriculumRepositoryWrapper repo;
    SubjectInfoService subjectInfoService;

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
                getCurriculumSubjects(curriculumDto, newCurriculum);

        newCurriculum.setCurriculumSubjects(curriculumSubjects);
        return repo.save(newCurriculum);
    }

    @Override
    public Curriculum update(Integer id, CurriculumDto curriculumDto) {
        var curriculum = repo.findById(id).orElseThrow();
        curriculum.setDenotation(curriculumDto.getDenotation());

        Set<CurriculumSubject> curriculumSubjects = getCurriculumSubjects(curriculumDto, curriculum);

        curriculum.setCurriculumSubjects(curriculumSubjects);

        return curriculum;
    }

    private Set<CurriculumSubject> getCurriculumSubjects(CurriculumDto curriculumDto, Curriculum curriculum) {
        Map<Integer, SubjectInfo> subjectsById =
                subjectInfoService.getByIds(List.copyOf(curriculumDto.getSubjectIdsByCiphers().values()))
                        .stream()
                        .collect(toMap(SubjectInfo::getId, i -> i));

        return curriculumDto.getSubjectIdsByCiphers().entrySet()
                .stream()
                .map(entry -> {
                    var curriculumSubject = new CurriculumSubject();
                    curriculumSubject.setCipher(entry.getKey());
                    curriculumSubject.setCurriculum(curriculum);
                    curriculumSubject.setSubjectInfo(subjectsById.get(entry.getValue()));
                    return curriculumSubject;
                })
                .collect(toSet());
    }

    @Override
    public void deleteById(Integer id) {
        repo.deleteById(id);
    }
}
