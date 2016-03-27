package io.sandbox.stream.examples;

public enum TopicInfo {
    PATH(String.format("projects/%s/topics/", "pub-sub-poc")),
    TOPIC_NAME("myTopic");

    private String value;

    private TopicInfo(String s) {
        this.value = s;
    }

    public String getValue() { return value; }
}
