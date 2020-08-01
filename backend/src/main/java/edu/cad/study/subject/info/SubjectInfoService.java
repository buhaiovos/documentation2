package edu.cad.study.subject.info;

import edu.cad.entities.Control;
import edu.cad.entities.ControlDictionary;
import edu.cad.entities.SubjectHeader;
import edu.cad.entities.SubjectInfo;
import edu.cad.study.EntityService;
import edu.cad.study.control.ControlDto;
import edu.cad.study.control.ControlService;
import edu.cad.study.control.dictionary.ControlDictionaryService;
import edu.cad.study.subject.header.SubjectHeaderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import static edu.cad.entities.ControlDictionary.COURSE_WORK;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class SubjectInfoService implements EntityService<SubjectInfo, Integer, SubjectInfoDto> {
    SubjectInfoRepositoryWrapper repo;
    SubjectHeaderService subjectHeaderService;
    ControlService controlService;
    ControlDictionaryService controlDictionaryService;

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
                .setSubjectHeader(subjectHeaderService
                        .findById(subjectInfo.subjectHeaderId())
                        .orElseThrow())
                .setSemester(subjectInfo.semester())
                .setSemestersDuration(subjectInfo.semestersDuration())
                .setEcts(subjectInfo.ects())
                .setLectures(subjectInfo.lectures())
                .setActualLectures(subjectInfo.actualLectures())
                .setLabs(subjectInfo.labs())
                .setActualLabs(subjectInfo.actualLabs())
                .setPractices(subjectInfo.practices())
                .setActualPractices(subjectInfo.actualPractices());

        Set<Control> controls = fetchNewControls(subjectInfo);

        newSubjectInfo.setControls(controls);

        return repo.save(newSubjectInfo);
    }

    private Set<Control> fetchNewControls(SubjectInfoDto subjectInfo) {
        return ofNullable(subjectInfo.controls())
                .stream().flatMap(Stream::of)
                .map(ControlDto::id)
                .map(controlService::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(toSet());
    }

    @Override
    public SubjectInfo update(Integer id, SubjectInfoDto subjectInfo) {
        SubjectInfo updated = repo.findById(id).orElseThrow();
        updated.setSemester(subjectInfo.semester())
                .setSubjectHeader(subjectHeaderService
                        .findById(subjectInfo.subjectHeaderId())
                        .orElseThrow())
                .setSemestersDuration(subjectInfo.semestersDuration())
                .setLectures(subjectInfo.lectures())
                .setActualLectures(subjectInfo.actualLectures())
                .setLabs(subjectInfo.labs())
                .setActualLabs(subjectInfo.actualLabs())
                .setPractices(subjectInfo.practices())
                .setActualPractices(subjectInfo.actualPractices())
                .setEcts(subjectInfo.ects());

        updated.setControls(fetchNewControls(subjectInfo));

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

    public boolean hasCourseWork(SubjectInfo si) {
        ControlDictionary courseWork = controlDictionaryService.findById(COURSE_WORK).orElseThrow();

        for (SubjectHeader subjectHeader : si.getSubjectHeader().getSubSubjects()) {
            for (SubjectInfo element : subjectHeader.getSubjectInfo()) {
                if (element.hasControlOfType(courseWork)) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean isCourseWork(SubjectInfo si) {
        ControlDictionary courseWork = controlDictionaryService.findById(COURSE_WORK).orElseThrow();
        return si.hasControlOfType(courseWork);
    }

    public double getEctsHoursWithoutExam(SubjectInfo si) {
        if (hasCourseWork(si))
            return si.getEctsHours() - 30;

        return si.getEctsHours();
    }
}
