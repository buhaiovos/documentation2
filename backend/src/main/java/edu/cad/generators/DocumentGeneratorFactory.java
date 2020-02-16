package edu.cad.generators;

import edu.cad.domain.DocumentType;
import edu.cad.generators.curriculum.DocumentGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class DocumentGeneratorFactory {
    private final Map<DocumentType, DocumentGenerator> generatorsByType;

    public DocumentGenerator forType(DocumentType type) {
        return generatorsByType.get(type);
    }
}
