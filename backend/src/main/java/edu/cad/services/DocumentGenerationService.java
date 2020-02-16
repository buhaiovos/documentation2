package edu.cad.services;

import edu.cad.domain.DocumentType;
import edu.cad.generators.DocumentGenerator;
import edu.cad.generators.DocumentGeneratorFactory;
import edu.cad.services.filenames.FileNameResolvingService;
import edu.cad.services.storage.StorageService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DocumentGenerationService {
    StorageService storageService;
    FileNameResolvingService nameResolvingService;
    DocumentGeneratorFactory generatorFactory;

    public byte[] generateDocument(DocumentType documentType) {
        final String templateFileName = nameResolvingService.resolveForDocument(documentType);
        final byte[] template = storageService.getFile(templateFileName);
        return generateDocument(documentType, template);
    }

    private byte[] generateDocument(DocumentType documentType, byte[] templateBytes) {
        DocumentGenerator generator = generatorFactory.forType(documentType);
        try (
                var template = new ByteArrayInputStream(templateBytes);
                var output = new ByteArrayOutputStream();
                Workbook generated = generator.generate(template)
        ) {
            generated.write(output);
            return output.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("IO exception during document generation", e);
        }
    }

}
