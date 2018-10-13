package factory;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class PersonDao implements Dao {
	private List<Connection> connections;

	public PersonDao(List<Connection> connections) {
		this.connections =  connections;
	}

	@Override
	public void save(Row row) {
		// TODO: Implement specialized logic for this method
	}

	@Override
	public List<Person> find(String query) {
		// TODO: Implement specialized logic for this method
		return new ArrayList<>();
	}

}
