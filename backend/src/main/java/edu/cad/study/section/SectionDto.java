package edu.cad.study.section;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import edu.cad.study.DropdownOption;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record SectionDto(
        int id,
        String denotation,
        boolean optional,
        DropdownOption cycle
) {}
