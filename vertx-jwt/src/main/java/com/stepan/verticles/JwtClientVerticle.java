package com.stepan.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.http.HttpClientRequest;

/**
 * Created by stepanm on 25.1.2016.
 */
public class JwtClientVerticle extends AbstractVerticle {

    private String assertedToken = null;

    @Override
    public void start(Future<Void> startFuture) throws Exception {

        HttpClientOptions options = new HttpClientOptions()
                .setKeepAlive(false)
                .setDefaultPort(config().getInteger("http.port",8080))
                .setDefaultHost("localhost");


        // first, send request to obtain jwt token

        vertx.createHttpClient(options)
                .getNow("/api/newToken", response -> {
                    response.bodyHandler(body -> {
                        assertedToken = body.toString();
                        System.out.println("[CLIENT] Requested token: " + assertedToken);
                    });
                    // based on generated token, make some request on private resource

                });


        // asynchronously try for protected resource

        vertx.setPeriodic(2000, handler -> {
            System.out.println("[CLIENT] new request on /api/protected");
            HttpClientRequest request =  vertx.createHttpClient(options)
                    .get("/api/protected", response -> {
                        System.out.println("[CLIENT] Response received.");
                        response.bodyHandler(body -> {
                            System.out.println("[CLIENT] Received body of response: " + body);

                        });
                    });
            request.headers().set("Authorization", "Bearer " + assertedToken);
            request.end();


//            headers: {
//                "Authorization": "Bearer " + $('#token').html().substring(15)
        });



        startFuture.complete();
    }
}
