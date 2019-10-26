package edu.cad.study.persistence;

import edu.cad.entities.ControlDictionary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ControlDictionaryRepository extends JpaRepository<ControlDictionary, Integer> {
}
