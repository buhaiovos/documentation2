package edu.cad.controllers;

import edu.cad.domain.DocumentType;
import edu.cad.services.filenames.FileNameResolvingService;
import edu.cad.services.storage.StorageService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/templates")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class TemplateController {
    StorageService storageService;
    FileNameResolvingService docNameResolver;

    public TemplateController(@Lazy StorageService storageService,
                              @Lazy FileNameResolvingService docNameResolver) {
        this.storageService = storageService;
        this.docNameResolver = docNameResolver;
    }

    @GetMapping
    public ResponseEntity<byte[]> getTemplate(@RequestParam("template") DocumentType documentType) {
        final String fileName = docNameResolver.resolveForDocument(documentType);
        final byte[] file = storageService.getFile(fileName);

        return DocumentResponseUtil.buildResponseForXlsFileBytes(documentType, file);
    }

    @PostMapping
    public void uploadTemplate(@RequestParam("template") DocumentType documentType, @RequestParam("file") MultipartFile template) throws IOException {
        final String fileName = docNameResolver.resolveForDocument(documentType);
        storageService.uploadFile(fileName, template.getBytes());
    }
}
