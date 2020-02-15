package edu.cad.study.load.subject;

import edu.cad.domain.FormOfEducation;
import edu.cad.entities.SubjectStudyLoad;
import edu.cad.utils.k3.SourceOfFinancing;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubjectStudyLoadService {
    private final SubjectStudyLoadRepositoryWrapper subjectLoadDao;

    public List<SubjectStudyLoad> search(int semester,
                                         FormOfEducation formOfEducation,
                                         SourceOfFinancing sourceOfFinancing) {
        return subjectLoadDao.findAll()
                .stream()
                .filter(bySemester(semester))
                .filter(byFormOfEducation(formOfEducation))
                .filter(byFinancing(sourceOfFinancing))
                .collect(Collectors.toList());
    }

    private Predicate<SubjectStudyLoad> bySemester(int semester) {
        return switch (semester) {
            case 1:
                yield load -> (load.getSubjectInfo() != null) && (load.getSubjectInfo().getSemester() % 2 == 1);
            case 2:
                yield load -> (load.getSubjectInfo() != null) && (load.getSubjectInfo().getSemester() % 2 == 0);
            default:
                throw new IllegalArgumentException(format("Unsupported semester nubmer: %d", semester));
        };
    }

    private Predicate<SubjectStudyLoad> byFormOfEducation(FormOfEducation formOfEducation) {
        return load -> formOfEducation.getDbId() == load.getEducationForm().getId();
    }

    private Predicate<SubjectStudyLoad> byFinancing(SourceOfFinancing sourceOfFinancing) {
        return load -> sourceOfFinancing.equals(load.getSourceOfFinancing());
    }

}
