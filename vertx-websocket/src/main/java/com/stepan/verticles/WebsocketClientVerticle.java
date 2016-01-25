package com.stepan.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpClient;

/**
 * Created by stepanm on 25.1.2016.
 */
public class WebsocketClientVerticle extends AbstractVerticle {
    @Override
    public void start() throws Exception {

        HttpClient client = vertx.createHttpClient();
        client.post(8080, "localhost", "/", event -> {
           System.out.println("Response achieved!");
        });
    }
}
