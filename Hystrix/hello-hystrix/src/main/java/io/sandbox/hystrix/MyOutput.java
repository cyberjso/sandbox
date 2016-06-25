package io.sandbox.hystrix;

public class MyOutput {
    private String id;
    private String name;

    public MyOutput() {}

    public MyOutput(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return "MyOutput{" + "id='" + id + '\'' + ", name='" + name + '\'' + '}';
    }
}
