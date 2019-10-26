package edu.cad.study.persistence;

import edu.cad.entities.ControlDictionary;
import edu.cad.year.YearProvider;
import org.springframework.stereotype.Component;

@Component
public class ControlDictionaryRepositoryWrapper extends YearAwareRepositoryWrapper<ControlDictionary, Integer> {
    public ControlDictionaryRepositoryWrapper(ControlDictionaryRepository repo, YearProvider yearProvider) {
        super(repo, yearProvider);
    }
}
