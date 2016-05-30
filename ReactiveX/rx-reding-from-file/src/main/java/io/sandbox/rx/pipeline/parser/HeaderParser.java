package io.sandbox.rx.pipeline.parser;


import io.sandbox.rx.pipeline.domain.Header;

public class HeaderParser implements Parser {

    public Header parse(String line) {
        String[] columns = line.split("|");
        return new Header(columns[1], columns[2]);
    }

    

}
