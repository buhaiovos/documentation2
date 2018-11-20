package edu.cad.domain;

import lombok.Getter;

@Getter
public enum FormOfEducation {
    INTRAMURAL(1, ""),
    EXTRAMURAL(2, "з"),
    EVENING(3, "в");

    private final int dbId;
    private final String groupCipherPrefix;

    FormOfEducation(int dbId, String groupCipherPrefix) {
        this.dbId = dbId;
        this.groupCipherPrefix = groupCipherPrefix;
    }
}
