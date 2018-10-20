package edu.cad.services.years;

import edu.cad.services.filenames.FileNameResolvingService;
import edu.cad.services.storage.StorageService;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Set;

@Component
public class DbYearsTrackingServiceImpl implements DbYearsTrackingService {
    private static final String TEMP_FILE_PREFIX = "years";
    private static final String TEMP_FILE_SUFFIX = ".docmentation";

    private final FileNameResolvingService fileNameResolvingService;
    private final StorageService storageService;
    private final YearsFileHandler yearsFileHandler;

    public DbYearsTrackingServiceImpl(FileNameResolvingService fileNameResolvingService, StorageService storageService) {
        this.fileNameResolvingService = fileNameResolvingService;
        this.storageService = storageService;
        this.yearsFileHandler = new YearsFileHandler();
    }

    @Override
    public Set<Integer> getAll() {
        final File yearsFile = getYearsFIle();
        return yearsFileHandler.readYearsFromFile(yearsFile);
    }

    private File getYearsFIle() {
        final byte[] yearsFileContent = getYearsFileBytes();
        return getTempFileFromBytes(yearsFileContent);
    }

    private byte[] getYearsFileBytes() {
        String dbYearsFileName = fileNameResolvingService.resolveForDatabaseYearsFile();
        return storageService.getFile(dbYearsFileName);
    }

    private File getTempFileFromBytes(byte[] yearsFile) {
        File tempFile = prepareTempFile();
        try (var fileOutputStream = new FileOutputStream(tempFile)) {
            fileOutputStream.write(yearsFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            tempFile.deleteOnExit();
        }

        return tempFile;
    }

    private File prepareTempFile() {
        try {
            return File.createTempFile(TEMP_FILE_PREFIX, TEMP_FILE_SUFFIX);
        } catch (IOException e) {
            throw new RuntimeException("Failed to create temp file for years.", e);
        }
    }

    @Override
    public void registerNewYear(int year) {
        final File yearsFile = getYearsFIle();
        yearsFileHandler.addYearToFile(year, yearsFile);
        saveUpdateYearsFile(yearsFile);
    }

    private void saveUpdateYearsFile(File yearsFile) {
        final String dbYearsFileName = fileNameResolvingService.resolveForDatabaseYearsFile();
        try {
            final byte[] bytes = Files.readAllBytes(yearsFile.toPath());
            storageService.uploadFile(dbYearsFileName, bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
