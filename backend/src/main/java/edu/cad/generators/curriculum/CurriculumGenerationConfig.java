package edu.cad.generators.curriculum;

import edu.cad.domain.DocumentType;
import edu.cad.generators.k3.FormK3Generator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
class CurriculumGenerationConfig {
    @Bean
    public Map<DocumentType, DocumentGenerator> generatorsByType(CurriculumGenerator curriculumGenerator,
                                                                 WorkingPlanGenerator workingPlanGenerator,
                                                                 FormK3Generator formK3Generator) {
        return Map.of(
                DocumentType.CURRICULUM, curriculumGenerator,
                DocumentType.WORK_PLAN, workingPlanGenerator,
                DocumentType.K3, formK3Generator
        );
    }
}
