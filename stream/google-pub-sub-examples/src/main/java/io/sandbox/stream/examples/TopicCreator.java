package io.sandbox.stream.examples;

import com.google.api.services.pubsub.Pubsub;
import com.google.api.services.pubsub.model.Topic;

import java.io.IOException;

public class TopicCreator {
    private Pubsub client;

    public TopicCreator(Pubsub client) {
        this.client = client;
    }

    public Topic create(TopicInfo name) {
        try {
            return client
                    .projects()
                    .topics()
                    .create(TopicInfo.PATH.getValue() + name.getValue(), new Topic())
                    .execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
