package io.sandbox.stream.examples;


import com.google.api.services.pubsub.Pubsub;

public class Main {

    public static  void  main(String[] args) {
        Pubsub client = new PubsubClientBuilder().build();
        try {
            new MessageSender(client).send("cozinha mais!!");
        } catch (TopicNotFoundException e) {
            new TopicCreator(client).create(TopicInfo.TOPIC_NAME);
            new MessageSender(client).send("cozinha mais!!");
        }
    }

}
