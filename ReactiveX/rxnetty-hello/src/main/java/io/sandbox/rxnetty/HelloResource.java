package io.sandbox.rxnetty;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.logging.LogLevel;
import io.reactivex.netty.RxNetty;
import io.reactivex.netty.pipeline.PipelineConfigurators;
import io.reactivex.netty.protocol.http.server.HttpServer;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import io.reactivex.netty.protocol.http.server.HttpServerResponse;
import io.reactivex.netty.protocol.http.server.RequestHandler;
import rx.Observable;

public class HelloResource {
    private final int port = 8080;

    public HttpServer<ByteBuf, ByteBuf> buildHello()  {
       return RxNetty.newHttpServerBuilder(port, new RequestHandler<ByteBuf, ByteBuf>() {

            public Observable<Void> handle(HttpServerRequest<ByteBuf> request, HttpServerResponse<ByteBuf> response) {
                if (request.getPath().contains("/api/hello")) {
                    response.setStatus(HttpResponseStatus.OK);
                    response.writeString("Server returning ok");
                }  else {
                    response.setStatus(HttpResponseStatus.NOT_FOUND);
                }
                return response.close();
            }
        })
        .pipelineConfigurator(PipelineConfigurators.<ByteBuf, ByteBuf>httpServerConfigurator())
        .enableWireLogging(LogLevel.ERROR)
        .build();
    }

}
