package io.sandbox.rx.naosei;

import com.google.common.collect.Lists;
import io.sandbox.rx.pipeline.FileObservableBuilder;
import javafx.util.Pair;
import org.apache.commons.lang3.StringUtils;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Transforms a CSV  file into a map, partitioning the ids and skiping the column names
 *
 * i.E:
 * id | value
 * ----+---------
 * 4  | VAlue 3
 * 5  | VAlue 5
 * 6  | VAlue 6
 * 7  | VAlue 7
 * 8  | VAlue 8
 * 9  | VAlue 9
 * 10 | VAlue 10
 * 11 | VAlue 11
 *
 * Map("ids" -> "id=4&id=5"),
 * Map("ids" -> "id=6&id=7"),
 * Map("ids" -> "id=8&id=9"),
 * Map("ids" -> "id=10&id=11"),
 **/
public class DbDumpParser {

    public void process(String fileName) {

        new FileObservableBuilder()
                .build(fileName)
                .skip(2)
                .map(new Func1<String, Pair<String, String>>() {

                    public Pair<String, String> call(String line) {
                        String[] args = StringUtils.split(line, '|');
                        return new Pair<String, String>(args[0], StringUtils.trim(args[1]));
                    }

                })
                .filter(new Func1<Pair<String, String>, Boolean>() {

                    public Boolean call(Pair<String, String> stringStringPair) {
                        return !stringStringPair.getValue().contentEquals("null");
                    }

                })
                .map(new Func1<Pair<String, String>, String>() {

                    public String call(Pair<String, String> stringStringPair) {
                        return StringUtils.trim(stringStringPair.getKey());
                    }

                })
                .toList()
                .flatMap(new Func1<List<String>, Observable<List<String>>>() {

                    public Observable<List<String>> call(List<String> strings) {
                        return Observable.from(Lists.partition(strings, 2));
                    }

                })
                .flatMap(new Func1<List<String>, Observable<String>>() {

                    public Observable<String> call(List<String> strings) {
                        return Observable.from(
                                new String[]{"Map(\"ids\" -> \"" +
                                        strings.stream().map(item -> "id=" + item).collect(Collectors.joining("&")) + "\"),"});
                    }

                })
                .subscribe(new Action1<String>() {

                    public void call(String s) {
                        System.out.println(s);
                    }


                });

        }

}
