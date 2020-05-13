package edu.cad.study;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record DropdownOption(Integer id, String text) {
    private static final DropdownOption EMPTY = new DropdownOption(null, "...");

    public static DropdownOption empty() {
        return EMPTY;
    }
}
