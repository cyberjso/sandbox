import com.netflix.dyno.jedis.DynoJedisClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Queue;

public class Reader implements Runnable {
	private static final Logger logger = LogManager.getLogger(Reader.class);
	private DynoJedisClient dynoJedisClient;
	private List<String> keys;

	public Reader(DynoJedisClient dynoJedisClient, List<String> keys) {
		this.dynoJedisClient = dynoJedisClient;
		this.keys =  keys;
	}

	@Override
	public void run() {
		logger.info("Starting Reader");

		int index = 0;
		while (!keys.isEmpty()) {
			sleep(9000);

			if (index >= keys.size()) index = 0;

			String key =  keys.get(index);

			logger.info("Reading {} ",key);
			String content  =  dynoJedisClient.get(key);

			logger.info("Content for {} has {} in size", Optional.ofNullable(content).orElse("").length(),  key);
			index++;
		}
	}

	private void sleep(int sleepingTime) {
		try {
			Thread.sleep(sleepingTime);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

}
