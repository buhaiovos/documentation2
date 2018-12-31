package edu.cad.services.years;

import java.util.Set;

//@Service
public class DbYearsTrackingServiceMockingImpl implements DbYearsTrackingService {
    @Override
    public Set<Integer> getAll() {
        return Set.of(2016);
    }

    @Override
    public void registerNewYear(int year) {
        throw new UnsupportedOperationException("Mocking service");
    }
}
