package io.sandbox.rxnetty;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.logging.LogLevel;
import io.reactivex.netty.RxNetty;
import io.reactivex.netty.pipeline.PipelineConfigurators;
import io.reactivex.netty.protocol.http.server.HttpServer;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import io.reactivex.netty.protocol.http.server.HttpServerResponse;
import io.reactivex.netty.protocol.http.server.RequestHandler;
import io.sandbox.rxnetty.contract.Service;
import rx.Observable;

public class EntryPoint {
    private Service service;
    private ObjectMapper mapper;

    public EntryPoint(Service service) {
        this.service = service;
        this.mapper = new ObjectMapper();
    }

    public HttpServer<ByteBuf, ByteBuf> build() {

        return RxNetty.newHttpServerBuilder(8080, new RequestHandler<ByteBuf, ByteBuf>() {
            public Observable<Void> handle(HttpServerRequest<ByteBuf> request, HttpServerResponse<ByteBuf> response) {
                if (request.getPath().contains("/api/allRecords")) {
                    return service.getAll().flatMap(output -> {
                        try {
                            response.setStatus(HttpResponseStatus.OK);
                            return response.writeStringAndFlush(mapper.writeValueAsString(output));
                        } catch (Exception e) {
                            response.setStatus(HttpResponseStatus.INTERNAL_SERVER_ERROR);
                            return response.close();
                        }
                    });
                } else {
                    response.setStatus(HttpResponseStatus.NOT_FOUND);
                    return  response.close();
                }
            }
        })
        .pipelineConfigurator(PipelineConfigurators.<ByteBuf, ByteBuf>httpServerConfigurator())
        .enableWireLogging(LogLevel.DEBUG)
        .build();
    }
}
