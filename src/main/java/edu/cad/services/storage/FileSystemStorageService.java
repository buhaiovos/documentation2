package edu.cad.services.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Slf4j
@Deprecated
public class FileSystemStorageService implements StorageService {
    @Override
    public byte[] getFile(String fileName) {
        final Path path = Paths.get(fileName);
        return getBytesQuietly(path);
    }

    private byte[] getBytesQuietly(Path path) {
        try {
            return Files.readAllBytes(path);
        } catch (IOException e) {
            log.error("Failed to read file from given path: {}", path.toAbsolutePath().toString());
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Override
    public void uploadFile(String fileName, byte[] fileData) {
        if (Files.exists(Paths.get(fileName))) {
            new File(fileName).delete();
        }
        final Path filePath = createNewBlankFileAndReturnPath(fileName);
        storeDocument(fileData, filePath);
    }

    @Override
    public void uploadFile(String fileName, File file) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteAllFiles() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean exists(String fileName) {
        throw new UnsupportedOperationException();
    }

    private void storeDocument(byte[] fileData, Path filePath) {
        try (ByteArrayInputStream fileStream = new ByteArrayInputStream(fileData)) {
            Files.copy(fileStream, filePath);
        } catch (IOException e) {
            log.error("Failed to upload document", e);
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private Path createNewBlankFileAndReturnPath(String path) {
        final var newFile = new File(path);
        newFile.getParentFile().mkdirs();
        return newFile.toPath();
    }
}