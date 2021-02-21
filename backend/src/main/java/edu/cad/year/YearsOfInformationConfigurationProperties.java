package edu.cad.year;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.util.List;

import static java.util.stream.Collectors.toList;

@ConfigurationProperties(prefix = "years-of-information")
@ConstructorBinding
public class YearsOfInformationConfigurationProperties {
    private final List<Short> list;
    @Getter
    private final List<Short> sorted;

    public YearsOfInformationConfigurationProperties(List<Short> list) {
        this.list = list;
        this.sorted = this.list.stream().sorted().collect(toList());
    }
}
