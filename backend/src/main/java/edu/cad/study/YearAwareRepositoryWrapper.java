package edu.cad.study;

import edu.cad.entities.YearTracked;
import edu.cad.year.YearProvider;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public abstract class YearAwareRepositoryWrapper<T extends YearTracked, ID> {
    JpaRepository<T, ID> repo;
    YearProvider yearProvider;

    public List<T> findAll() {
        return repo.findAll()
                .stream()
                .filter(entity -> entity.getYearOfInformation() == yearProvider.getCurrent())
                .collect(toList());
    }

    public Optional<T> findById(ID id) {
        return repo.findById(id);
    }

    public T save(T entity) {
        entity.setYearOfInformation(yearProvider.getCurrent());
        return repo.save(entity);
    }

    public void deleteById(ID id) {
        if (repo.existsById(id)) repo.deleteById(id);
    }
}
