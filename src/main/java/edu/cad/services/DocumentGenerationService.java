package edu.cad.services;

import edu.cad.Document;
import edu.cad.generators.DocumentGenerator;
import edu.cad.services.filenames.FileNameResolvingService;
import edu.cad.services.storage.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
@Slf4j
public class DocumentGenerationService {
    private final StorageService storageService;
    private final FileNameResolvingService nameResolvingService;

    public DocumentGenerationService(StorageService storageService, FileNameResolvingService nameResolvingService) {
        this.storageService = storageService;
        this.nameResolvingService = nameResolvingService;
    }

    public byte[] generateDocument(Document document) {
        final String templateFileName = nameResolvingService.resolveForDocument(document);
        final byte[] template = storageService.getFile(templateFileName);

        return generateDocument(document, template);
    }

    private byte[] generateDocument(Document document, byte[] templateBytes) {
        DocumentGenerator generator = DocumentGenerator.forDocumentType(document);

        try (var template = new ByteArrayInputStream(templateBytes);
             var output = new ByteArrayOutputStream()
        ) {
            Workbook generated = generator.generate(template);
            generated.write(output);
            return output.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("IO exception during document generation", e);
        }
    }

}
