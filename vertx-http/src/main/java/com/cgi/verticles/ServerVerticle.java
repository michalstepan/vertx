package com.cgi.verticles;

import com.cgi.common.CommandHandler;
import com.cgi.dto.Request;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.http.HttpServerRequest;

/**
 * Created by stepanm on 18.1.2016.
 */
public class ServerVerticle extends AbstractVerticle {

    @Override
    public void start() throws Exception {
        try {
            HttpServerOptions options = new HttpServerOptions().setMaxWebsocketFrameSize(1000000);
            HttpServer server = vertx.createHttpServer(options);

            server.requestHandler((HttpServerRequest handleReq) -> {
                System.out.println("[SERVER] Request from client.");
                Request req = new Request();
                req.setMethod(handleReq.method().toString());
                req.setVersion(handleReq.version().toString());
                req.setPath(handleReq.path());
                req.setQuery(handleReq.query());
                req.setUri(handleReq.uri());
                req.setHeaders(handleReq.headers());

                handleReq = new CommandHandler(handleReq, req).handleCommand(event -> {
                    if (event.isSuccessfullyParsed()) {
                        System.out.println("[SERVER] parse successful.");
                    } else {
                        System.out.println("[SERVER] parse unsuccessful.");
                    }
                });

//                handleReq.response().setStatusCode(200);
//                handleReq.response().headers().set("Content-Length", Integer.toString(jwtToken.length()));

            });
            server.listen(config().getInteger("http.port", 8080), "localhost", event -> {
                if (event.succeeded()) {
                    System.out.println("[SERVER] Server listening on localhost:" + config().getInteger("http.port"));
                } else {
                    System.out.println("[SERVER] Server failed to start: " + event.cause().getMessage());
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
