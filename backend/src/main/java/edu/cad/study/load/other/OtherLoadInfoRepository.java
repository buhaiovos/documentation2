package edu.cad.study.load.other;

import edu.cad.entities.OtherLoadInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OtherLoadInfoRepository extends JpaRepository<OtherLoadInfo, Integer> {
}
