package edu.cad.study.load.subject.distributed;

import edu.cad.entities.DistributedSubjectStudyLoad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistributedSubjectStudyLoadRepository extends JpaRepository<DistributedSubjectStudyLoad, Integer> {
}
