package io.sandbox.rx.pipeline.parser;


import io.sandbox.rx.pipeline.domain.Entity;

public interface Parser {

    public Entity parse(String line);
}
