import com.datastax.driver.core.*;
import io.netty.util.internal.StringUtil;

import java.util.Arrays;

public class Main {
	//https://docs.datastax.com/en/developer/java-driver/3.4/upgrade_guide/migrating_from_astyanax/configuration/

	public static void main(String[] args) {
		System.out.println("Starting the driver");

		String hosts  =  System.getenv("HOSTS");

		if (StringUtil.isNullOrEmpty(hosts))
			throw new RuntimeException("A comma separated list needs to set as HOSTS env variable");

		Session session = buildSession(hosts.split(","));

		session.execute("use my_keyspace_v2");
		readTest(session);
		writeTest(session);
		session.close();
	}

	private static void readTest(Session session) {
		System.out.println("Querying cluster");
		ResultSet resultSet = session.execute("select key, value from table1");

		System.out.println("Fetching results");
		resultSet.forEach(row -> {
			String key =  row.getString(0);
			String value = row.getString(1);
			System.out.println(String.format("key: %s - value: %s", key, value));
		});
	}

	private static void writeTest(Session session) {
		PreparedStatement preparedStatement  =  session.prepare("insert into table1 (key, value) values (?, ?)");
		int batches = 100;
		int records = 1000;

		for (int i = 0; i< batches; i++) {
			System.out.println("Starting batch " + i);
			BatchStatement batch = new BatchStatement();

			for (int x = 0; x < records; x++)
				batch.add(preparedStatement.bind(String.format("key-%s-%s", i, x), "value_ " + i));

			session.execute(batch);
		}

	}

	private static Session buildSession(String[] nodes) {
		System.out.println("Connecting to: " + Arrays.toString(nodes));
		QueryOptions options =  new QueryOptions().setConsistencyLevel(ConsistencyLevel.LOCAL_QUORUM);
		Cluster cluster = Cluster.builder()
				.addContactPoints(nodes)
				.withQueryOptions(options)
				.withPort(9042)
				.build();
		return cluster.connect();
	}

}
