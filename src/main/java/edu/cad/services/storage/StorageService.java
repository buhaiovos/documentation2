package edu.cad.services.storage;

public interface StorageService {
    byte[] getFile(String fileName);

    void uploadFile(String fileName, byte[] fileData);
}
