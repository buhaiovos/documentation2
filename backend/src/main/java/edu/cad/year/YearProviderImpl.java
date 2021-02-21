package edu.cad.year;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Primary
class YearProviderImpl implements YearProvider {
    private final YearsOfInformationConfigurationProperties years;
    private final AtomicInteger currentYear;

    public YearProviderImpl(YearsOfInformationConfigurationProperties years) {
        this.years = years;
        List<Short> sorted = this.years.getSorted();
        this.currentYear = new AtomicInteger(sorted.get(sorted.size() - 1));
    }

    @Override
    public short getCurrent() {
        return currentYear.shortValue();
    }

    void setCurrentYear(int year) {
        currentYear.getAndSet(year);
    }
}
