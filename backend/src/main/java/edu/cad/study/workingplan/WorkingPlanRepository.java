package edu.cad.study.workingplan;

import edu.cad.entities.WorkingPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkingPlanRepository extends JpaRepository<WorkingPlan, Integer> {
    @Query(value = """
            select c
            from Curriculum c
            where
                c.type = :type and
                c.yearOfInformation = :year
            """)
    List<WorkingPlan> findAllByTypeAndYear(@Param("type") String type, @Param("year") short year);
}
