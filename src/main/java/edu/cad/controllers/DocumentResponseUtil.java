package edu.cad.controllers;

import edu.cad.Document;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.nio.charset.Charset;

import static org.apache.commons.lang3.StringUtils.join;

class DocumentResponseUtil {
    static ResponseEntity<byte[]> buildResponseForXlsFileBytes(Document document, byte[] file) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(new MediaType("application", "ms-excel", Charset.forName("UTF-8")))
                .contentLength(file.length)
                .header("Content-disposition", join("attachment; filename*=UTF-8''", document.name(), ".xls"))
                .body(file);
    }
}
