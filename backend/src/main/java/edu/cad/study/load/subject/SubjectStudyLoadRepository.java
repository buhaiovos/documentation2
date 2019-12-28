package edu.cad.study.load.subject;

import edu.cad.entities.SubjectStudyLoad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectStudyLoadRepository extends JpaRepository<SubjectStudyLoad, Integer> {
}
