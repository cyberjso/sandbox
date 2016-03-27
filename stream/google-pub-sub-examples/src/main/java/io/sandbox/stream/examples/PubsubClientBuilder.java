package io.sandbox.stream.examples;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.util.Utils;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.services.pubsub.Pubsub;
import com.google.api.services.pubsub.PubsubScopes;

import java.io.IOException;

public class PubsubClientBuilder {
    public static String APPLICATION_NAME = "pub-sub-poc";

    public Pubsub build()  {
        GoogleCredential credential = getGoogleCredential();
        if (credential.createScopedRequired()) credential = credential.createScoped(PubsubScopes.all());

        HttpRequestInitializer initializer = new HttpInitializer(credential);
        return new Pubsub.Builder(  Utils.getDefaultTransport(),
                                    Utils.getDefaultJsonFactory(),
                                    initializer)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    private GoogleCredential getGoogleCredential() {
        try {
            return GoogleCredential.fromStream(this.getClass().getResourceAsStream("/auth.json"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
