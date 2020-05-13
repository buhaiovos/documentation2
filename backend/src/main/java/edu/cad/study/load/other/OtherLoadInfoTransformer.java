package edu.cad.study.load.other;

import com.google.common.collect.ImmutableMap;
import edu.cad.entities.AcademicGroup;
import edu.cad.entities.OtherLoadInfo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class OtherLoadInfoTransformer {
    private final OtherLoadDistributionService service;

    public OtherLoadInfoDto toDto(OtherLoadInfo from) {
        long id = from.getId();
        String name = from.getLoadHeader().getLoadType().name();
        String dataKey = from.getLoadHeader().getObjectOfWork().name() + " " + getGroupCiphers(from);
        double subtractedValue = from.getCalculatedHours() - service.getAllDistributedHoursForLoadById(id);

        return new OtherLoadInfoDto()
                .setId(id)
                .setName(name)
                .setElements(ImmutableMap.of(dataKey, subtractedValue));
    }

    private String getGroupCiphers(OtherLoadInfo from) {
        return from.getGroups()
                .stream()
                .map(AcademicGroup::getCipher)
                .collect(Collectors.joining(", "));
    }
}
