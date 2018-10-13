package factory;

import java.util.List;

public interface Dao<T extends  Row> {

	void save(T row);

	List<T> find(String query);

}
