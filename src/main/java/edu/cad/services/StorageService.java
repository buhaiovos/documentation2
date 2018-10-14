package edu.cad.services;

public interface StorageService {
    byte[] getFile(String fileName);

    void uploadFile(String filename, byte[] fileData);
}
