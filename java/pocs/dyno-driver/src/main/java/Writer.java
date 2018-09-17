import com.netflix.dyno.jedis.DynoJedisClient;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Writer implements Runnable {
	private static final Logger logger = LogManager.getLogger(Writer.class);
	private DynoJedisClient dynoJedisClient;
	private List<String> keys;
	private Map<String, String> testContent = new HashMap<>();

	public Writer(DynoJedisClient dynoJedisClient, List<String> keys) {
		this.dynoJedisClient = dynoJedisClient;
		this.keys = keys;
		testContent.put("heavy", StringUtils.repeat("*", 100000000));
		testContent.put("light", StringUtils.repeat("*", 102400));
	}

	public void run() {
		logger.info("Starting writer");

		int index = 0;
		while (!keys.isEmpty()) {
			if (index >= keys.size()) index = 0;

			String key =  keys.get(index);
			logger.info("Writing {} ",key);

			dynoJedisClient.set(key, getTestContentBy(index));
			logger.info("key {} successfully set", key);

			index++;
			sleep(3000);
		}
	}

	private String getTestContentBy(int executionIndex) {
		return executionIndex % 2 == 0 ? testContent.get("heavy") : testContent.get("light");
	}

	private void sleep(int sleepingTime) {
		try {
			Thread.sleep(sleepingTime);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
}
