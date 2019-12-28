package edu.cad.study.control;

import edu.cad.entities.Control;
import edu.cad.entities.ControlDictionary;
import edu.cad.entities.SubjectInfo;
import edu.cad.study.EntityService;
import edu.cad.study.control.dictionary.ControlDictionaryService;
import edu.cad.study.subject.info.SubjectInfoService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ControlService implements EntityService<Control, Integer, ControlDto> {
    ControlRepositoryWrapper repo;
    SubjectInfoService subjectInfoService;
    ControlDictionaryService controlDictionaryService;

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
        final SubjectInfo subjectInfo =
                subjectInfoService.findById(control.getSubjectId()).orElseThrow();
        final ControlDictionary controlDictionary =
                controlDictionaryService.findById(control.getTypeId()).orElseThrow();

        final var newControl = new Control();
        newControl.setSemester(control.getSemester());
        newControl.setSubjectInfo(subjectInfo);
        newControl.setType(controlDictionary);

        return repo.save(newControl);
    }

    @Override
    public Control update(Integer id, ControlDto control) {
        final Control existingControl = repo.findById(id).orElseThrow();
        final SubjectInfo subjectInfo =
                subjectInfoService.findById(control.getSubjectId()).orElseThrow();
        final ControlDictionary controlDictionary =
                controlDictionaryService.findById(control.getTypeId()).orElseThrow();

        existingControl.setType(controlDictionary)
                .setSubjectInfo(subjectInfo)
                .setSemester(control.getSemester());

        return existingControl;
    }

    @Override
    public void deleteById(Integer id) {
        repo.deleteById(id);
    }
}
