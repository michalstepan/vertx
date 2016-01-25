package com.stepan.verticles;

import com.stepan.common.CarAPI;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.ext.web.Router;

/**
 * Created by stepanm on 25.1.2016.
 */
public class RestVerticle extends AbstractVerticle {

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        CarAPI.generateCars();

        Router router = Router.router(vertx);

        router.route("/").handler(event -> {
            event.response()
                    .putHeader("content-type","text/html")
                    .setStatusCode(200)
                    .end("<h1>Hello from the other side</h1>");
        });

        router.get("/api/cars").handler(CarAPI::getAll);
        router.get("/api/cars/:id").handler(CarAPI::getOne);
        router.post("/api/cars").handler(CarAPI::addOne);
        router.put("/api/cars/:id").handler(CarAPI::updateOne);
        router.delete("/api/cars/:id").handler(CarAPI::deleteOne);

        System.out.println("REST api exposed. Try one of the following commands:");
        System.out.println("GET /api/cars");
        System.out.println("POST /api/cars");
        System.out.println("GET /api/cars/[id]");
        System.out.println("PUT /api/cars/[id]");
        System.out.println("DELETE /api/cars/[id]");

        vertx.createHttpServer().requestHandler(router::accept).listen(config().getInteger("http.port", 8080), event -> {
            if (event.succeeded()) {
                startFuture.complete();
            } else {
                startFuture.fail(event.cause());
            }
        });

    }
}
