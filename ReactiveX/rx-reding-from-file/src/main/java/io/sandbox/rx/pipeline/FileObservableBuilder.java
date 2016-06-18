package io.sandbox.rx.pipeline;

import rx.Observable;
import rx.Subscriber;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileObservableBuilder  {

    public Observable build(String  fileName) {

    return Observable.create(new Observable.OnSubscribe<String>() {

        public void call(Subscriber<? super String> subscriber) {
            try {
                Files.lines(Paths.get(FileObservableBuilder.class.getResource("/" + fileName).toURI()), Charset.defaultCharset())
                        .forEach(line ->
                                subscriber.onNext(line)
                        );
                subscriber.onCompleted();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    });

    }

}
