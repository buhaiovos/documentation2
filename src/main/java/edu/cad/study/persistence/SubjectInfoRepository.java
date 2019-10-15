package edu.cad.study.persistence;

import edu.cad.entities.SubjectInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectInfoRepository extends JpaRepository<SubjectInfo, Integer> {
}
