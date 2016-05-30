package io.sandbox.rx;

import javafx.util.Pair;
import rx.Observable;
import rx.Subscriber;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class FileReader {

    public void read(String fileName) {

        Observable.create(new Observable.OnSubscribe<String>() {

            public void call(Subscriber<? super String> subscriber) {
                try {
                    Files.lines(Paths.get(FileReader.class.getResource("/" + fileName).toURI()), Charset.defaultCharset())
                            .forEach(line -> Arrays.stream(line.split(" "))
                                    .forEach( word -> subscriber.onNext(word)));
                    subscriber.onCompleted();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

        })
        .filter(word -> !word.trim().isEmpty())
        .groupBy(word -> word.trim())
        .flatMap(words -> words.count().map(counter -> new Pair(words.getKey(), counter)))
        .toList()

        .subscribe(pairs ->
                pairs.forEach(
                        item -> {
                            System.out.println("printing *****");
                            System.out.println("key: " + item.getKey() + " value: " + item.getValue());
                        }));
    }

}
