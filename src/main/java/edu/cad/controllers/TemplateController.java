package edu.cad.controllers;

import edu.cad.Document;
import edu.cad.services.filenames.FileNameResolvingService;
import edu.cad.services.storage.StorageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/templates")
public class TemplateController {
    private final StorageService storageService;
    private final FileNameResolvingService docNameResolver;

    public TemplateController(StorageService storageService, FileNameResolvingService docNameResolver) {
        this.storageService = storageService;
        this.docNameResolver = docNameResolver;
    }

    @GetMapping
    public ResponseEntity<byte[]> getTemplate(@RequestParam("template") Document document) {
        final String fileName = docNameResolver.resolveForDocument(document);
        final byte[] file = storageService.getFile(fileName);

        return DocumentResponseUtil.buildResponseForXlsFileBytes(document, file);
    }

    @PostMapping
    public void uploadTemplate(@RequestParam("template") Document document, @RequestParam("file") MultipartFile template) throws IOException {
        final String fileName = docNameResolver.resolveForDocument(document);
        storageService.uploadFile(fileName, template.getBytes());
    }
}