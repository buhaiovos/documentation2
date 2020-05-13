package edu.cad.controllers;

import edu.cad.domain.DocumentType;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.nio.charset.Charset;

import static org.apache.commons.lang3.StringUtils.join;

class DocumentResponseUtil {
    static ResponseEntity<byte[]> buildResponseForXlsFileBytes(DocumentType documentType, byte[] file) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(new MediaType("application", "ms-excel", Charset.forName("UTF-8")))
                .contentLength(file.length)
                .header("Content-disposition", join("attachment; filename*=UTF-8''", documentType.name(), ".xls"))
                .body(file);
    }
}
