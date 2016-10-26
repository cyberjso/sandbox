package io.sandbox.rxnetty;

import io.netty.buffer.ByteBuf;
import io.reactivex.netty.RxNetty;
import io.reactivex.netty.pipeline.PipelineConfigurator;
import io.reactivex.netty.protocol.http.client.HttpClient;
import io.reactivex.netty.protocol.http.client.HttpClientPipelineConfigurator;
import io.reactivex.netty.protocol.http.client.HttpClientRequest;
import io.reactivex.netty.protocol.http.client.HttpClientResponse;
import rx.functions.Func1;

import java.nio.charset.Charset;

public class ServiceClient {

    public void callService() {
        PipelineConfigurator<HttpClientResponse<ByteBuf>,
                        HttpClientRequest<ByteBuf>> configurator = new HttpClientPipelineConfigurator();
        HttpClient<ByteBuf, ByteBuf>  client = RxNetty.createHttpClient("localhost", 8080, configurator);

        client.submit(HttpClientRequest.createGet("/api/allRecords"))
                .flatMap(response -> response.getContent()
                        .map(byteBuf -> byteBuf.toString(Charset.defaultCharset())))
                .map(new Func1<String, String>() {

                    public String call(String s) {
                        System.out.println("resulr  " + s);
                        return s;
                    }
                }).toBlocking();
    }
}
