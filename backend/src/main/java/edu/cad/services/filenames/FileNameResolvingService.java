package edu.cad.services.filenames;

import edu.cad.domain.DocumentType;

public interface FileNameResolvingService {
    String resolveForDocument(DocumentType documentType);

    String resolveForDatabaseYearsFile();
}
