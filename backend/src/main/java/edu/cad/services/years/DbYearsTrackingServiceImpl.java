package edu.cad.services.years;

import edu.cad.services.filenames.FileNameResolvingService;
import edu.cad.services.storage.StorageService;
import edu.cad.utils.databaseutils.DatabaseSwitcher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Set;

@Service
@Lazy
@Slf4j
class DbYearsTrackingServiceImpl implements DbYearsTrackingService {
    private static final String TEMP_FILE_PREFIX = "years";
    private static final String TEMP_FILE_SUFFIX = ".documentation";

    private final FileNameResolvingService fileNameResolvingService;
    private final StorageService storageService;
    private final YearsFileHandler yearsFileHandler;
    private final DatabaseSwitcher databaseSwitcher;

    public DbYearsTrackingServiceImpl(@Lazy FileNameResolvingService fileNameResolvingService,
                                      @Lazy StorageService storageService,
                                      @Lazy DatabaseSwitcher databaseSwitcher) {
        this.fileNameResolvingService = fileNameResolvingService;
        this.storageService = storageService;
        this.databaseSwitcher = databaseSwitcher;

        this.yearsFileHandler = new YearsFileHandler();
    }

    @PostConstruct
    private void init() {
        log.info("Initializing...");

        String yearsFileName = fileNameResolvingService.resolveForDatabaseYearsFile();
        if (!storageService.exists(yearsFileName)) {
            log.info("No years file is found storage. Creating new...");
            createYearsFileWithCurrentYear();
        }

        log.info("...Done");
    }

    private void createYearsFileWithCurrentYear() {
        final int databaseYear = databaseSwitcher.getDatabaseYear();
        final File yearsFile = prepareTempFile();
        yearsFileHandler.addYearToFile(databaseYear, yearsFile);
        storageService.uploadFile(fileNameResolvingService.resolveForDatabaseYearsFile(), yearsFile);
    }

    @Override
    public Set<Integer> getAll() {
        final File yearsFile = getYearsFile();
        return yearsFileHandler.readYearsFromFile(yearsFile);
    }

    private File getYearsFile() {
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
        final File yearsFile = getYearsFile();
        yearsFileHandler.addYearToFile(year, yearsFile);
        saveUpdatedYearsFile(yearsFile);
    }

    private void saveUpdatedYearsFile(File yearsFile) {
        final String dbYearsFileName = fileNameResolvingService.resolveForDatabaseYearsFile();
        try {
            final byte[] bytes = Files.readAllBytes(yearsFile.toPath());
            storageService.uploadFile(dbYearsFileName, bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
