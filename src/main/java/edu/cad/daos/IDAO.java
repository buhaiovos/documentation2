package edu.cad.daos;

import edu.cad.entities.interfaces.IDatabaseEntity;

import java.util.List;
import java.util.Optional;

public interface IDAO <T extends IDatabaseEntity>{

	List<T> getAll();

	T get(int id);

	Optional<T> getById(int id);

	T update(T instance);

	boolean create(T instance);

	boolean delete(int id);
}
