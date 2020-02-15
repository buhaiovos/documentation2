package edu.cad.study.subject.info;

import edu.cad.entities.Control;
import edu.cad.entities.SubjectInfo;
import edu.cad.study.EntityService;
import edu.cad.study.control.ControlService;
import edu.cad.study.subject.header.SubjectHeaderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class SubjectInfoService implements EntityService<SubjectInfo, Integer, SubjectInfoDto> {
    SubjectInfoRepositoryWrapper repo;
    SubjectHeaderService subjectHeaderService;
    ControlService controlService;

    @Override
    public List<SubjectInfo> getAll() {
        return repo.findAll();
    }

    @Override
    public Optional<SubjectInfo> findById(Integer id) {
        return repo.findById(id);
    }

    @Override
    public SubjectInfo create(SubjectInfoDto subjectInfo) {
        var newSubjectInfo = new SubjectInfo()
                .setSemester(subjectInfo.getSemester())
                .setSemestersDuration(subjectInfo.getSemestersDuration())
                .setLectures(subjectInfo.getLectures())
                .setActualLectures(subjectInfo.getActualLectures())
                .setLabs(subjectInfo.getLabs())
                .setActualLabs(subjectInfo.getActualLabs())
                .setPractices(subjectInfo.getPractices())
                .setActualPractices(subjectInfo.getActualPractices())
                .setSubjectHeader(
                        subjectHeaderService.findById(subjectInfo.getSubjectHeaderId())
                                .orElseThrow()
                );
        Set<Control> controls = Arrays.stream(subjectInfo.getControlsIds())
                .mapToObj(controlService::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());

        newSubjectInfo.setControls(controls);

        return repo.save(newSubjectInfo);
    }

    @Override
    public SubjectInfo update(Integer id, SubjectInfoDto subjectInfo) {
        SubjectInfo updated = repo.findById(id).orElseThrow();
        updated.setSemester(subjectInfo.getSemester())
                .setSemestersDuration(subjectInfo.getSemestersDuration())
                .setLectures(subjectInfo.getLectures())
                .setActualLectures(subjectInfo.getActualLectures())
                .setLabs(subjectInfo.getLabs())
                .setActualLabs(subjectInfo.getActualLabs())
                .setPractices(subjectInfo.getPractices())
                .setActualPractices(subjectInfo.getActualPractices())
                .setSubjectHeader(
                        subjectHeaderService.findById(subjectInfo.getSubjectHeaderId())
                                .orElseThrow()
                );
        Set<Control> controls = Arrays.stream(subjectInfo.getControlsIds())
                .mapToObj(controlService::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());

        updated.setControls(controls);

        return repo.save(updated);
    }

    @Override
    public void deleteById(Integer id) {
        repo.deleteById(id);
    }

    public List<SubjectInfo> getByIds(List<Integer> ids) {
        return repo.findAll()
                .stream()
                .filter(subjectInfo -> ids.contains(subjectInfo.getId()))
                .collect(toList());
    }
}
