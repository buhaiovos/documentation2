package edu.cad.documentelements.k3columns;

final class K3OtherLoadTokens {
    private K3OtherLoadTokens() {
    }

    private static final String TOKEN_BEGINNING = "#k3(OC)";

    public static final String FACULTY = TOKEN_BEGINNING + "faculty";
    public static final String YEAR = TOKEN_BEGINNING + "year";
    public static final String GROUP = TOKEN_BEGINNING + "group";
    public static final String NUMBER_OF_STUDENTS = TOKEN_BEGINNING + "stNum";
    public static final String HOURS = TOKEN_BEGINNING + "hours";
}
