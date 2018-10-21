package edu.cad.services.years;

import java.util.Set;

public interface DbYearsService {
    Set<Integer> getAll();

    int getCurrent();

    void switchToYear(int year);
}
