package com.stepan.verticles;


import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.auth.jwt.JWTOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.JWTAuthHandler;
import io.vertx.ext.web.handler.StaticHandler;

/**
 * Created by stepanm on 25.1.2016.
 */
public class JwtServerVerticle extends AbstractVerticle {

    @Override
    public void start(Future<Void> startFuture) throws Exception {

        // using router to expose api

        Router router = Router.router(vertx);

        // initialize jwt keystore

        JWTAuth jwtAuth = JWTAuth.create(vertx, new JsonObject()
                .put("keyStore", new JsonObject()
                        .put("type", "jceks")
                        .put("path", "keystore.jceks")
                        .put("password", "secret")));

        JWTOptions jwtOptions = new JWTOptions()
                .setExpiresInMinutes(1L)
                .setIssuer("Michal")
                .setSubject("Deal");


        // protect api with JWT token
        router.route("/api/*").handler(JWTAuthHandler.create(jwtAuth, "/api/newToken"));

        router.get("/api/newToken").handler(event -> {
            event.response().putHeader("Content-Type","text/plain");
            String generatedToken = jwtAuth.generateToken(new JsonObject().put("message","Hi my friend client"), jwtOptions);
            event.response().headers().set("Content-Length", Integer.toString(generatedToken.length()));
            event.response().end(generatedToken);
            System.out.println("[SERVER] JWT Token generated.");
        });

        System.out.println("Exposed GET /api/newToken");

        // secret api
        router.get("/api/protected").handler(context -> {
            context.response().putHeader("Content-Type", "text/plain");
            context.response().end("Hooray, you have access through JWT.");
        });

        System.out.println("Exposed GET /api/protected");

        router.route().handler(StaticHandler.create("web"));

        vertx.createHttpServer().requestHandler(router::accept).listen(config().getInteger("http.port", 8080));
        System.out.println("[SERVER] listening on port : " + config().getInteger("http.port"));

        startFuture.complete();
    }


}
