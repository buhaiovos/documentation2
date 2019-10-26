package edu.cad.services.storage;

import java.io.File;

public interface StorageService {
    byte[] getFile(String fileName);

    void uploadFile(String fileName, byte[] fileData);

    void uploadFile(String fileName, File file);

    void deleteAllFiles();

    boolean exists(String fileName);
}
