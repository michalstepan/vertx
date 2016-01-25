package com.stepan;

import com.stepan.verticles.WebsocketClientVerticle;
import com.stepan.verticles.WebsocketServerVerticle;
import io.vertx.core.Vertx;

/**
 * Created by stepanm on 25.1.2016.
 */
public class WebsocketApplication {
    public static void main(String... args){

        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new WebsocketServerVerticle());
        vertx.deployVerticle(new WebsocketClientVerticle());
    }
}
