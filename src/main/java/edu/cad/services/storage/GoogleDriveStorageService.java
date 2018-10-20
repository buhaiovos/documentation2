package edu.cad.services.storage;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

import static java.lang.String.format;

@Service
@Primary
public class GoogleDriveStorageService implements StorageService {
    /**
     * Be sure to specify the name of your application. If the application name is {@code null} or
     * blank, the application will log a warning. Suggested format is "MyCompany-ProductName/1.0".
     */
    private static final String APPLICATION_NAME = "Cad-Documentation/1.0";

    /**
     * Global instance of the HTTP transport.
     */
    private HttpTransport httpTransport;

    /**
     * Global instance of the JSON factory.
     */
    private final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    /**
     * Global Drive API client.
     */
    private Drive drive;

    @PostConstruct
    public void init() {
        try {
            httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            Credential credential = authorize();
            drive = new Drive
                    .Builder(httpTransport, JSON_FACTORY, credential)
                    .setApplicationName(APPLICATION_NAME)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize Google Drive connection.", e);
        }
    }

    /**
     * Authorizes the installed application to access user's protected data.
     */
    private Credential authorize() throws Exception {
        return GoogleCredential
                .fromStream(GoogleDriveStorageService.class.getResourceAsStream("/kpi-cad-documentation-42eb34ecb6a0.json"))
                .createScoped(Collections.singleton(DriveScopes.DRIVE_FILE));
    }


    @Override
    public byte[] getFile(String fileName) {
        final String id = getFileIdByName(fileName).orElseThrow(
                () -> new RuntimeException(format("File with given name: <%s> is not found", fileName))
        );
        return downloadFileByIdQuietly(id);
    }

    private Optional<String> getFileIdByName(String fileName) {
        FileList result = getFilesListQuietly();
        for (File file : result.getFiles()) {
            if (file.getName().equals(fileName)) {
                return Optional.of(file.getId());
            }
        }

        return Optional.empty();
    }

    private byte[] downloadFileByIdQuietly(String id) {
        try {
            return downloadFileById(id);
        } catch (IOException e) {
            throw new RuntimeException(format("Failed to download file with id: <%s>", e));
        }
    }

    private byte[] downloadFileById(String id) throws IOException {
        try (var byteArrayOutputStream = new ByteArrayOutputStream()) {
            drive.files().get(id).executeMediaAndDownloadTo(byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        }
    }

    private FileList getFilesListQuietly() {
        try {
            return drive.files().list().execute();
        } catch (IOException e) {
            throw new RuntimeException("Failed to call Drive.files().list()", e);
        }
    }

    @Override
    public void uploadFile(String fileName, byte[] fileData) {
        getFileIdByName(fileName).ifPresent(this::deleteFileByIdQuietly);
        uploadFileQuietly(fileName, fileData);
    }

    private void deleteFileByIdQuietly(String id) {
        try {
            drive.files().delete(id).execute();
        } catch (IOException e) {
            throw new RuntimeException(format("Failed to delete file with id: <%s>", id));
        }
    }

    private void uploadFileQuietly(String fileName, byte[] fileData) {
        var fileMetadata = new File();
        fileMetadata.setName(fileName);
        var fileContent = new ByteArrayContent("application/vnd.ms-excel", fileData);
        createQuietly(fileMetadata, fileContent);
    }

    private void createQuietly(File fileMetadata, ByteArrayContent fileContent) {
        try {
            drive.files().create(fileMetadata, fileContent).execute();
        } catch (IOException e) {
            throw new RuntimeException(format("Failed to create file: <%s>", fileMetadata), e);
        }
    }
}
