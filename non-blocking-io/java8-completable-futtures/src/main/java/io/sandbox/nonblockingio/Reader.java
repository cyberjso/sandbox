package io.sandbox.nonblockingio;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Reader {
    private List<String> files = Arrays.asList("file-3.txt", "file-1.txt", "file-2.txt");
    private static final Logger logger = LogManager.getLogger(Reader.class);

    public void read() {
        CompletionService service = new ExecutorCompletionService<List<String>>(Executors.newFixedThreadPool(3));
        files.stream().forEach(file ->
                service.submit(() ->
                    FileUtils.readLines(
                        new File(this.getClass().getClassLoader().getResource(file).getFile()))));

        files.stream().forEach(file -> {
            try  {
                Future<List<String>> fileContent = service.take();
                logger.info(fileContent.get().size());
            } catch (Exception e ) {
                throw new RuntimeException(e);
            }
        });
    }



}
