package io.sandbox.rx.pipeline.domain;


public class Header extends Entity {
    private String id;
    private String name;

    public Header(String id, String  name) {
        super("001");
        this.id = id;
        this.name =  name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
