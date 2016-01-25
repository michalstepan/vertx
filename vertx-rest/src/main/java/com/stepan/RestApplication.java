package com.stepan;

import com.stepan.verticles.RestVerticle;
import io.vertx.core.Vertx;

/**
 * Created by stepanm on 25.1.2016.
 */
public class RestApplication {
    public static void main(String... args){

        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new RestVerticle());
    }
}
