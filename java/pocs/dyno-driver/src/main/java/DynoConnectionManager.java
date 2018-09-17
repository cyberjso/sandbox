import com.google.gson.Gson;
import com.netflix.config.ConfigurationManager;
import com.netflix.dyno.connectionpool.Host;
import com.netflix.dyno.connectionpool.HostSupplier;
import com.netflix.dyno.connectionpool.TokenMapSupplier;
import com.netflix.dyno.connectionpool.impl.lb.AbstractTokenMapSupplier;
import com.netflix.dyno.contrib.ArchaiusConnectionPoolConfiguration;
import com.netflix.dyno.jedis.DynoJedisClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class DynoConnectionManager {
	private static final Logger logger = LogManager.getLogger(DynoConnectionManager.class);

	public DynoJedisClient connect(String clusterName, List<Map<String, String>> nodes) {
		logger.info("Connecting to {} nodes", clusterName);

		ConfigurationManager.getConfigInstance().setProperty("dyno." + clusterName + ".retryPolicy", "RetryNTimes:3:true");

		DynoJedisClient client  = new DynoJedisClient.Builder().withApplicationName(clusterName)
				.withDynomiteClusterName(clusterName)
				.withCPConfig(
						new ArchaiusConnectionPoolConfiguration(clusterName)
								.withTokenSupplier(buildTokenSupplier(nodes))
								.setMaxConnsPerHost(1)
				)
				.withHostSupplier(buildHosSupplier(nodes))
				.build();

		logger.info("Connected to {}", clusterName);

		return client;
	}

	private TokenMapSupplier buildTokenSupplier(List<Map<String, String>> nodes) {
		String json  =  new Gson().toJson(nodes);
		System.out.println(json);
		TokenMapSupplier testTokenMapSupplier = new AbstractTokenMapSupplier() {
			public String getTopologyJsonPayload(String hostname) {
				return json;
			}

			public String getTopologyJsonPayload(Set<Host> activeHosts) {
				return json;
			}
		};

		return testTokenMapSupplier;
	}


	private static HostSupplier buildHosSupplier(List<Map<String, String>> nodes ) {
		return () -> nodes
				.stream()
				.map(node -> new Host(node.get("ip"), 8102, node.get("rack"), Host.Status.Up))
				.collect(Collectors.toList());
	}



}
