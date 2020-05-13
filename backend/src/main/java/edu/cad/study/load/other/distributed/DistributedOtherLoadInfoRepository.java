package edu.cad.study.load.other.distributed;

import edu.cad.entities.DistributedOtherLoadInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistributedOtherLoadInfoRepository extends JpaRepository<DistributedOtherLoadInfo, Integer> {
}
