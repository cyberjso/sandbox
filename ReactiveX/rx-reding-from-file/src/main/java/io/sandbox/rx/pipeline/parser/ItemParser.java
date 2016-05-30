package io.sandbox.rx.pipeline.parser;


import io.sandbox.rx.pipeline.domain.Item;

public class ItemParser implements Parser {

    public Item parse(String line) {
        String[] columns = line.split("|");
        return new Item(columns[1], columns[2], columns[3]);
    }

}
