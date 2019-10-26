package edu.cad.study.persistence;

import edu.cad.entities.WorkingPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkingPlanRepository extends JpaRepository<WorkingPlan, Integer> {
}