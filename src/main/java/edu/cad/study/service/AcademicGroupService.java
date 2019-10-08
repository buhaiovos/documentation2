package edu.cad.study.service;

import edu.cad.entities.AcademicGroup;
import edu.cad.study.persistence.AcademicGroupRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AcademicGroupService {
    AcademicGroupRepository repository;

    public List<AcademicGroup> getAll() {
        return repository.findAll();
    }
}
