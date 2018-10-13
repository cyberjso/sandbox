package factory;

import javax.management.InstanceNotFoundException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class DaoFactory {

	/**
	 * Want specific aspects of object construction hidden from consumers? A factory could be used for that intent.
	 * On this example, We could construct DAO  implementations connecting  to different database servers
	 * and this aspect be abstracted aways from consumers.
	 *
	 * That could be used to Implement Dual write pattern, for instance.
	 *
	 * @param name
	 * @return
	 */
	public static Dao getInstance(String name) {
		if (name.equalsIgnoreCase("personDao"))
			return new PersonDao(buildConnections());
		if (name.equalsIgnoreCase("companyDao"))
			return new CompanyDao(buildConnections());
		else
			throw new RuntimeException(String.format("Instance %s is not supported by this factory", name));

	}

	private static List<Connection> buildConnections() {
		return new ArrayList<>();
	}

}
