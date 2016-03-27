package io.sandbox.stream.examples;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpStatusCodes;
import com.google.api.services.pubsub.Pubsub;
import com.google.api.services.pubsub.model.PublishRequest;
import com.google.api.services.pubsub.model.PublishResponse;
import com.google.api.services.pubsub.model.PubsubMessage;
import com.google.common.collect.ImmutableList;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class MessageSender {
    private Pubsub client;

    public MessageSender(Pubsub client) {
        this.client = client;
    }

    public PublishResponse send(String messageContent) {
        PubsubMessage message = buildPubsubMessage(messageContent);
        PublishRequest request = new PublishRequest();
        request.setMessages(ImmutableList.of(message));
        return publish(request);
    }

    private PubsubMessage buildPubsubMessage(String messageContent) {
        PubsubMessage message = new PubsubMessage();
        try {
            message.encodeData(messageContent.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return message;
    }

    private PublishResponse publish(PublishRequest request) {
        try {
            return send(request);
        } catch (GoogleJsonResponseException e) {
            if (e.getStatusCode() == HttpStatusCodes.STATUS_CODE_NOT_FOUND) {
                throw new TopicNotFoundException();
            }
            throw new RuntimeException(e);
        }
    }

    private PublishResponse send(PublishRequest request) throws GoogleJsonResponseException {
        try {
            return client
                    .projects()
                    .topics()
                    .publish(TopicInfo.PATH.getValue() + TopicInfo.TOPIC_NAME.getValue(), request)
                    .execute();
        } catch (IOException e) {
            throw (GoogleJsonResponseException) e;
        }
    }

}