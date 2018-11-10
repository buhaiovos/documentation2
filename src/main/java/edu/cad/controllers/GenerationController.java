package edu.cad.controllers;

import edu.cad.Document;
import edu.cad.services.DocumentGenerationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/document-generation")
public class GenerationController {
    private final DocumentGenerationService generationService;

    public GenerationController(DocumentGenerationService generationService) {
        this.generationService = generationService;
    }

    @GetMapping
    public ResponseEntity<byte[]> generateDocument(@RequestParam("document") Document document) {
        final byte[] bytes = generationService.generateDocument(document);
        return DocumentResponseUtil.buildResponseForXlsFileBytes(document, bytes);
    }
}
