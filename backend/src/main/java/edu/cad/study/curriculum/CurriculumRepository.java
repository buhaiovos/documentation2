package edu.cad.study.curriculum;

import edu.cad.entities.Curriculum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CurriculumRepository extends JpaRepository<Curriculum, Integer> {
    @Query(value = """
            select c
            from Curriculum c
            where
                c.type = :type and
                c.yearOfInformation = :year
            """)
    List<Curriculum> findAllByTypeAndYear(@Param("type") String type, @Param("year") short year);
}
