package edu.cad.services;

import edu.cad.Document;

public interface DocumentFileNameResolvingService {
    String getFileName(Document document);
}
