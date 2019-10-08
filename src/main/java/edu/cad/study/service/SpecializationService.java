package edu.cad.study.service;

import edu.cad.entities.Specialization;
import edu.cad.study.persistence.SpecializationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpecializationService {
    private final SpecializationRepository repo;

    public List<Specialization> getAll() {
        return repo.findAll();
    }
}
