package edu.cad.services.storage.google;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "google.drive.credentials")
@Getter
@Setter
class GoogleDriveConfigurationProperties extends GoogleDriveCredentials {

    GoogleDriveCredentials getCredentials() {
        return new GoogleDriveCredentials(
                this.type,
                this.projectId,
                this.privateKeyId,
                this.privateKey.replaceAll("\\\\n", "\n"),
                this.clientEmail,
                this.clientId,
                this.authUri,
                this.tokenUri,
                this.authProviderX509CertUrl,
                this.clientX509CertUrl
        );
    }
}
