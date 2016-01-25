package com.stepan.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.core.streams.Pump;

/**
 * Created by stepanm on 25.1.2016.
 */
public class WebsocketServerVerticle extends AbstractVerticle {
    @Override
    public void start() throws Exception {
        HttpServer server = vertx.createHttpServer();
        server.websocketHandler(event -> {
            Pump.pump(event,event).start();

        }).listen(8080,"localhost");
        System.out.println("[SERVER] probably listening");
    }
}
