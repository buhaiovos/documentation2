package edu.cad.daos;

import edu.cad.entities.interfaces.IDatabaseEntity;
import java.util.List;

public interface IDAO <T extends IDatabaseEntity>{
	
	public List<T> getAll();
	
	public T get(int id);
	
	public T update(T instance);
	
	public boolean create(T instance);
	
	public boolean delete(int id);
}
