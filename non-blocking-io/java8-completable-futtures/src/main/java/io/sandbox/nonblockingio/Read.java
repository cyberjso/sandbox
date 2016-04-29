package io.sandbox.nonblockingio;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Read {
    private List<String> files = Arrays.asList("file-3.txt", "file-1.txt", "file-2.txt");
    private static final Logger logger = LogManager.getLogger(Reader.class);
    private WordCounter counter = new WordCounter();

    public void read() {
        files.stream().forEach(file -> {
                    try {
                        counter.read(file)
                                .thenAcceptAsync(result ->
                                        logger.info("computation result: " + result.get(file))).get(30, TimeUnit.SECONDS);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (TimeoutException e) {
                        e.printStackTrace();
                    }
                }


        );
    }

}
