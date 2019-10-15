package edu.cad.study.persistence;

import edu.cad.entities.OtherLoadInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OtherLoadInfoRepository extends JpaRepository<OtherLoadInfo, Integer> {
}
