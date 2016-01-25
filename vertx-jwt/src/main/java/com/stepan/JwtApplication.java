package com.stepan;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

/**
 * Created by stepanm on 25.1.2016.
 */
public class JwtApplication {
    public static void main(String... args){
        System.out.println("Hello jwt");

        DeploymentOptions deploymentOptions = new DeploymentOptions()
                .setInstances(1)
                .setConfig(new JsonObject().put("http.port",8083));

        Vertx vertx = Vertx.vertx();

        vertx.deployVerticle("com.stepan.verticles.JwtServerVerticle", deploymentOptions, event -> {
            System.out.println("JWT Server Verticle successfully deployed: " + event.succeeded());
        });

        vertx.deployVerticle("com.stepan.verticles.JwtClientVerticle", deploymentOptions, event -> {
            System.out.println("JWT Client Verticle successfully deployed: " + event.succeeded());
        });
    }
}
