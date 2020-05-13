package edu.cad.study.load.subject;

import edu.cad.domain.StudyLoadType;
import edu.cad.entities.DistributedSubjectStudyLoad;
import edu.cad.study.load.subject.distributed.DistributedSubjectStudyLoadRepositoryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingDouble;

@Service
@RequiredArgsConstructor
public class SubjectLoadSubtractionService {
    private final DistributedSubjectStudyLoadRepositoryWrapper repo;
    private final SubjectStudyLoadService studyLoadService;

    public SubjectLoadDto subtractDistributedLoad(SubjectLoadDto subjectLoadDto) {
        var defensiveCopy = new SubjectLoadDto(subjectLoadDto);

        repo.findAll()
                .stream()
                .filter(load -> load.getTargetLoad().getId().equals(subjectLoadDto.getId()))
                .collect(
                        groupingBy(
                                DistributedSubjectStudyLoad::getStudyLoadType,
                                summingDouble(DistributedSubjectStudyLoad::getAmount)
                        )
                )
                .forEach((key, value) -> subtractDistributedHours(defensiveCopy, key, value));

        return defensiveCopy;
    }

    private void subtractDistributedHours(SubjectLoadDto deffensiveCopy, StudyLoadType type, Double distributedHours) {
        Double oldValue = deffensiveCopy.getLoadAmountByType().get(type);
        Double newValue = oldValue - distributedHours;
        deffensiveCopy.getLoadAmountByType().put(type, newValue);
    }
}
