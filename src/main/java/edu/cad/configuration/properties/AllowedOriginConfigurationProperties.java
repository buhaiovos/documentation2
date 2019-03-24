package edu.cad.configuration.properties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@Configuration
@ConfigurationProperties(prefix = "cad.allowed-origins")
@Setter @Getter @NoArgsConstructor
public class AllowedOriginConfigurationProperties {
    private Set<String> urls = new HashSet<>();
    private Set<String> headers = new HashSet<>();
}
