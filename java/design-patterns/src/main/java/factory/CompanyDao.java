package factory;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class CompanyDao implements Dao {
	private final List<Connection> connection;

	public CompanyDao(List<Connection> connections) {
		this.connection =  connections;
	}

	@Override
	public void save(Row row) {
		// TODO: Implement specialized logic for this method
	}

	@Override
	public List find(String query) {
		// TODO: Implement specialized logic for this method

		return new ArrayList();
	}
}
