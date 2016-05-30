package io.sandbox.rx.pipeline.domain;


public class Item extends Entity {
    private String id;
    private String value;
    private String name;

    public Item(String id, String value, String name) {
        super("002");
        this.name = name;
        this.value  = value;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

}
