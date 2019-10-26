package edu.cad.services.filenames;

import edu.cad.domain.Document;

public interface FileNameResolvingService {
    String resolveForDocument(Document document);

    String resolveForDatabaseYearsFile();
}
