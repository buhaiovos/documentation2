package edu.cad.services;

import edu.cad.controllers.dto.SubjectLoadDto;
import edu.cad.daos.DistributedStudyLoadDao;
import edu.cad.domain.StudyLoadType;
import edu.cad.entities.DistributedSubjectStudyLoad;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingDouble;

@Service
@RequiredArgsConstructor
public class SubjectLoadSubtractionService {
    private final DistributedStudyLoadDao distributedStudyLoadDao;

    public SubjectLoadDto subtractDistributedLoad(SubjectLoadDto subjectLoadDto) {
        var deffensiveCopy = new SubjectLoadDto(subjectLoadDto);

        distributedStudyLoadDao.getAllByTargetLoadId(subjectLoadDto.getId())
                .stream()
                .collect(
                        groupingBy(
                                DistributedSubjectStudyLoad::getStudyLoadType,
                                summingDouble(DistributedSubjectStudyLoad::getAmount)
                        )
                )
                .forEach((key, value) -> subtractDistributedHours(deffensiveCopy, key, value));

        return deffensiveCopy;
    }

    private void subtractDistributedHours(SubjectLoadDto deffensiveCopy, StudyLoadType type, Double distributedHours) {
        Double oldValue = deffensiveCopy.getLoadAmountByType().get(type);
        Double newValue = oldValue - distributedHours;
        deffensiveCopy.getLoadAmountByType().put(type, newValue);
    }
}
