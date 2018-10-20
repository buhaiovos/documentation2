package edu.cad.services.years;

import java.util.Set;

public interface DbYearsTrackingService {
    Set<Integer> getAll();

    void registerNewYear(int year);
}
