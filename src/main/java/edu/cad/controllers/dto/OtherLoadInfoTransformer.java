package edu.cad.controllers.dto;

import com.google.common.collect.ImmutableMap;
import edu.cad.entities.AcademicGroup;
import edu.cad.entities.OtherLoadInfo;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class OtherLoadInfoTransformer {
    public OtherLoadInfoDto toDto(OtherLoadInfo from) {
        long id = from.getId();
        String name = from.getLoadHeader().getLoadType().name();
        String dataKey = from.getLoadHeader().getObjectOfWork().name() + " " + getGroupCiphers(from);
        double dataValue = from.getCalculatedHours();

        return new OtherLoadInfoDto()
                .setId(id)
                .setName(name)
                .setElements(ImmutableMap.of(dataKey, dataValue));
    }

    private String getGroupCiphers(OtherLoadInfo from) {
        return from.getGroups()
                .stream()
                .map(AcademicGroup::getCipher)
                .collect(Collectors.joining(", "));
    }
}
