package edu.cad.services.storage.google;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
@AllArgsConstructor
@NoArgsConstructor
class GoogleDriveCredentials {
    String type;
    String projectId;
    String privateKeyId;
    String privateKey;
    String clientEmail;
    String clientId;
    String authUri;
    String tokenUri;
    String authProviderX509CertUrl;
    String clientX509CertUrl;
}
