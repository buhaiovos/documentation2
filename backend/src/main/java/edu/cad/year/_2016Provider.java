package edu.cad.year;

import org.springframework.stereotype.Component;

@Component
class _2016Provider implements YearProvider {
    @Override
    public short getCurrent() {
        return 2016;
    }
}
