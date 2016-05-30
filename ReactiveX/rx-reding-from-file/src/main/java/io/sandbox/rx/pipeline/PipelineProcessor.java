package io.sandbox.rx.pipeline;

import com.google.common.collect.ImmutableMap;
import io.sandbox.rx.pipeline.domain.Entity;
import io.sandbox.rx.pipeline.domain.Item;
import io.sandbox.rx.pipeline.domain.MasterDetail;
import io.sandbox.rx.pipeline.parser.HeaderParser;
import io.sandbox.rx.pipeline.parser.ItemParser;
import io.sandbox.rx.pipeline.parser.Parser;
import rx.Observable;
import rx.functions.Func1;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PipelineProcessor {

    private Map<String,Parser> parsers;

    public PipelineProcessor() {
        parsers  = ImmutableMap.of("001", new HeaderParser(), "002", new ItemParser());
    }

    public void doProcess() {
        new FileObservableBuilder().build("csvFile.txt").map(new Func1() {

            public Entity call(Object item) {
                String line = (String) item;
                String[] value = line.split("|");
                return parsers.get(value[0]).parse(line);
            }
        })
        .toList()
        .filter(x -> ((Entity) x).getIdFieldType().equals("001"))
        .flatMap(new Func1<List<Entity>, Observable<MasterDetail>>() {

            @Override
            public Observable<MasterDetail> call(List<Entity> entities) {
                List<Item> items = new ArrayList<Item>();

                return null;
            }

        });



    }

}
