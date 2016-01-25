package com.cgi;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

import java.io.FileNotFoundException;

/**
 * Created by stepanm on 18.1.2016.
 */
public class HttpApplication {

    public static void main(String... args) throws FileNotFoundException {

        DeploymentOptions deploymentOptions = new DeploymentOptions().setInstances(1);
        deploymentOptions.setConfig(new JsonObject().put("http.port",8082));
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle("com.cgi.verticles.ClientVerticle", deploymentOptions, event -> {
            System.out.println("Client Verticle successfully deployed: " + event.succeeded());
        });
        vertx.deployVerticle("com.cgi.verticles.ServerVerticle", deploymentOptions, event -> {
            System.out.println("Server Verticle successfully deployed: " + event.succeeded());
        });

    }
}
