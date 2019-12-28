package edu.cad.study.subject.info;

import edu.cad.entities.SubjectInfo;
import edu.cad.study.EntityService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class SubjectInfoService implements EntityService<SubjectInfo, Integer, SubjectInfoDto> {
    SubjectInfoRepositoryWrapper repo;

    @Override
    public List<SubjectInfo> getAll() {
        return null;
    }

    @Override
    public Optional<SubjectInfo> findById(Integer id) {
        return repo.findById(id);
    }

    @Override
    public SubjectInfo create(SubjectInfoDto academicGroup) {
        return null;
    }

    @Override
    public SubjectInfo update(Integer id, SubjectInfoDto updatedGroup) {
        return null;
    }

    @Override
    public void deleteById(Integer integer) {

    }
}
