package edu.cad.study.control;

import edu.cad.entities.Control;
import edu.cad.entities.ControlDictionary;
import edu.cad.entities.SubjectInfo;
import edu.cad.study.EntityService;
import edu.cad.study.control.dictionary.ControlDictionaryService;
import edu.cad.study.subject.info.SubjectInfoService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ControlService implements EntityService<Control, Integer, ControlDto> {
    ControlRepositoryWrapper repo;
    ControlDictionaryService controlDictionaryService;
    SubjectInfoService subjectInfoService;

    public ControlService(ControlRepositoryWrapper repo,
                          ControlDictionaryService controlDictionaryService,
                          @Lazy SubjectInfoService subjectInfoService) {
        this.repo = repo;
        this.controlDictionaryService = controlDictionaryService;
        this.subjectInfoService = subjectInfoService;
    }

    @Override
    public List<Control> getAll() {
        return repo.findAll();
    }

    @Override
    public Optional<Control> findById(Integer id) {
        return repo.findById(id);
    }

    @Override
    public Control create(ControlDto control) {
        SubjectInfo subjectInfo = subjectInfoService.findById(control.subjectId()).orElseThrow();

        final var newControl = new Control();
        newControl.setSubjectInfo(subjectInfo);
        newControl.setSemester(ofNullable(control.semester()).orElse(1));

        ofNullable(control.typeId())
                .flatMap(controlDictionaryService::findById)
                .ifPresent(newControl::setType);

        return repo.save(newControl);
    }

    @Override
    public Control update(Integer id, ControlDto control) {
        final Control existingControl = repo.findById(id).orElseThrow();

        final ControlDictionary controlType =
                controlDictionaryService.findById(control.typeId()).orElseThrow();
        existingControl
                .setType(controlType)
                .setSemester(control.semester());

        return existingControl;
    }

    @Override
    public void deleteById(Integer id) {
        repo.deleteById(id);
    }
}
