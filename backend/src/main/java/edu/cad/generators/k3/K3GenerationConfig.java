package edu.cad.generators.k3;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
class K3GenerationConfig {
    @Bean
    public List<AbstractK3Generator> k3Generators(OtherActivitiesPageGenerator otherActivitiesPageGenerator,
                                                  SubjectListPageGenerator subjectListPageGenerator) {
        return List.of(otherActivitiesPageGenerator, subjectListPageGenerator);
    }
}
