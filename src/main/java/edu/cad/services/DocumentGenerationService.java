package edu.cad.services;

import edu.cad.Document;
import edu.cad.generators.DocumentGenerator;
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
    private final DocumentFileNameResolvingService nameResolvingService;

    public DocumentGenerationService(StorageService storageService, DocumentFileNameResolvingService nameResolvingService) {
        this.storageService = storageService;
        this.nameResolvingService = nameResolvingService;
    }

    public byte[] generateDocument(Document document) {
        final String fileName = nameResolvingService.getFileName(document);
        final byte[] file = storageService.getFile(fileName);

        return generateDocument(document, file);
    }

    private byte[] generateDocument(Document document, byte[] file) {
        DocumentGenerator generator = DocumentGenerator.forDocumentType(document);

        try (var template = new ByteArrayInputStream(file);
             var output = new ByteArrayOutputStream()) {
            Workbook generated = generator.generate(template);
            generated.write(output);
            return output.toByteArray();
        } catch (IOException e) {
            log.error("IO exception during document generation", e);
            throw new RuntimeException(e);
        }
    }

}
