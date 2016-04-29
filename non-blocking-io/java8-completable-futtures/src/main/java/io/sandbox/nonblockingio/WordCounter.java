package io.sandbox.nonblockingio;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static java.nio.file.StandardOpenOption.READ;


public class WordCounter {

    public CompletableFuture<Map<String, Integer>> read(String fileName) {
        return open(fileName)
                .thenApply(this::countWordsPerLine)
                .thenApply(counters -> group(counters, fileName));

        /*return CompletableFuture
                .supplyAsync(() -> open(fileName))
                .thenApply(this::countWordsPerLine)
                .thenApply(counters -> group(counters, fileName))*/
    }


    private Map<String, Integer> group(Integer  perLine, String fileName) {
        return new HashMap<String, Integer>(){{put(fileName, perLine);}};
    }

    private Integer countWordsPerLine(ByteBuffer lines) {
        try {
            return new String(lines.array(), "UTF-8").split(" ").length;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //return lines.stream().map(line -> line.split(" ").length).collect(Collectors.toList());
    }

    private CompletableFuture<ByteBuffer> open(String fileName) {
        try {
            AsynchronousFileChannel ch = AsynchronousFileChannel.open(
                    Paths.get(WordCounter.class.getClassLoader().getResource(fileName).getPath().replaceFirst("^/(.:/)", "$1")), READ);

            CompletableFuture<ByteBuffer> completableFuture = new CompletableFuture<ByteBuffer>();

            ByteBuffer buffer = ByteBuffer.allocate(8192);
            ch.read(buffer, 0L, completableFuture, new CompletionHandler<Integer, Object>() {

                @Override
                public void completed(Integer result, Object attachment) {
                    completableFuture.complete(buffer);
                }

                @Override
                public void failed(Throwable exc, Object attachment) {
                    completableFuture.completeExceptionally(exc);
                }

            });

            return completableFuture;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
