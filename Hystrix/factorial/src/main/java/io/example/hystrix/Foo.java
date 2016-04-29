package io.example.hystrix;

public class Foo {
    private String content;

    public Foo(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
