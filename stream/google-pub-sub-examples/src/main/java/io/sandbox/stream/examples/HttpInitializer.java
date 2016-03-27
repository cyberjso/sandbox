package io.sandbox.stream.examples;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.*;
import com.google.api.client.util.ExponentialBackOff;

import java.io.IOException;

public class HttpInitializer implements HttpRequestInitializer {
    private Credential wrappedCredential;

    public HttpInitializer(Credential credential) {
        this.wrappedCredential = credential;
    }

    @Override
    public void initialize(HttpRequest request) throws IOException {
        request.setReadTimeout(5000);
        final HttpUnsuccessfulResponseHandler handler = new HttpBackOffUnsuccessfulResponseHandler(new ExponentialBackOff());
        request.setInterceptor(wrappedCredential);
        request.setUnsuccessfulResponseHandler(new HttpUnsuccessfulResponseHandler() {

            @Override
            public boolean handleResponse(HttpRequest request, HttpResponse response, boolean supportsRetry) throws IOException {
                if (wrappedCredential.handleResponse(request, response, supportsRetry )) return true;
                else if(handler.handleResponse(request, response, supportsRetry)) return true;
                else return false;
            }
        });
        request.setIOExceptionHandler(
                new HttpBackOffIOExceptionHandler(new ExponentialBackOff()));
    }

}
