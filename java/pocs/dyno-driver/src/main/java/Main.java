import com.netflix.dyno.jedis.DynoJedisClient;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
	private static final Logger logger = LogManager.getLogger(Main.class);

	public static void main(String args[]) throws InterruptedException {
		if (isMissingEnvVars()) return;

		String clusterName =  System.getenv("CLUSTER_NAME");
		List<Map<String, String>> nodes = parseClusterNodes(System.getenv("CLUSTER_NODES"));

		if (nodes.isEmpty()) {
			logger.error("Not able to build a list of cluster nodes");
			return;
		}

		DynoJedisClient dynoClient = new DynoConnectionManager().connect(clusterName, nodes);

		String testContent = StringUtils.repeat('*', 100000000);

		long sleepInterval = 5000;
		int ttl = 20;

		logger.info("Starting the test");

		while (true) {
			Thread.sleep(sleepInterval);
			UUID key = UUID.randomUUID();
			dynoClient.setex(key.toString(), ttl, testContent);

			logger.info("{} successfully inserted. Waiting for {} seconds to move next", clusterName, sleepInterval / 1000);
			Thread.sleep(sleepInterval);
		}
	}

	private static boolean isMissingEnvVars() {
		if (StringUtils.isEmpty(System.getenv("CLUSTER_NAME"))) {
			logger.error("Env variable {} needs to be set", "CLUSTER_NAME");
			return true;
		}

		if (StringUtils.isEmpty(System.getenv("CLUSTER_NODES"))) {
			logger.error("Env variable {} needs to be set", "CLUSTER_NODES");
			return true;
		}

		return false;
	}

	private static List<Map<String, String>> parseClusterNodes(String parameter) {
		return	Arrays.asList(parameter.split(";"))
				.stream()
				.filter(content -> content.contains(","))
				.map( node -> {
						String[] n = node.split(",");
						return buildNodeInfo(n[0], n[1], n[2]);
				}).collect(Collectors.toList());
	}

	private static Map<String, String> buildNodeInfo(String server, String rack, String token) {
		return new HashMap<String, String>() {{
			put("ip", server);
			put("token", token);
			put("hostname", server);
			put("rack", rack);
			put("zone", rack);
			put("dc", rack);
		}};
	}

}
