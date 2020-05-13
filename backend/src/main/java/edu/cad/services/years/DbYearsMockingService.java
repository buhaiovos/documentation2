package edu.cad.services.years;

import java.util.Set;

//@Service
//@Primary
public class DbYearsMockingService implements DbYearsService {
    @Override
    public Set<Integer> getAll() {
        return Set.of(2016);
    }

    @Override
    public int getCurrent() {
        return 2016;
    }

    @Override
    public void switchToYear(int year) {
        throw new UnsupportedOperationException("Mock service");
    }
}
