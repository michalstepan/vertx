package com.stepan.common;

import com.stepan.dto.Car;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by stepanm on 25.1.2016.
 */
public class CarAPI {

    public static Map<Integer, Car> myCollection = new LinkedHashMap<>();

    public static void generateCars(){
        Car ferrari = new Car("Ferrari","Enzo");
        myCollection.put(ferrari.getId(), ferrari);
        Car lamborghini = new Car("Lamborghini","Gallardo");
        myCollection.put(lamborghini.getId(), lamborghini);
    }

    public static void getAll(RoutingContext routingContext){
        routingContext.response()
                .putHeader("Content-Type","application/json; charset=utf-8")
                .end(Json.encodePrettily(myCollection.values()));
    }

    public static void getOne(RoutingContext routingContext) {
        String id = routingContext.request().getParam("id");
        if (id == null) {
            routingContext.response().setStatusCode(400).end();
        } else {
            Car car = myCollection.get(Integer.valueOf(id));
            routingContext.response().setStatusCode(200).putHeader("content-type", "application/json; charset=utf-8").end(Json.encodePrettily(car));
        }
    }

    public static void addOne(RoutingContext routingContext) {
        final Car car = Json.decodeValue(routingContext.getBodyAsString(), Car.class);
        myCollection.put(car.getId(), car);
        routingContext.response().setStatusCode(201).putHeader("content-type", "application/json; charset=utf-8").end(Json.encodePrettily(car));
    }

    public static void deleteOne(RoutingContext routingContext) {
        String id = routingContext.request().getParam("id");
        if (id == null) {
            routingContext.response().setStatusCode(400).end();
        } else {
            Integer idAsInteger = Integer.valueOf(id);
            myCollection.remove(idAsInteger);
        }
        routingContext.response().setStatusCode(204).end();
    }

    public static void updateOne(RoutingContext routingContext) {
        final String id = routingContext.request().getParam("id");
        JsonObject json = routingContext.getBodyAsJson();
        if (json == null){
            System.out.println("JSON OBJECT IS NULL");
        }
        if (id == null || json == null) {
            routingContext.response().setStatusCode(400).end();
        } else {
            final Integer idAsInteger = Integer.valueOf(id);
            Car car = myCollection.get(idAsInteger);
            if (car == null) {
                routingContext.response().setStatusCode(404).end();
            } else {
                car.setManufacturer(json.getString("manufacturer"));
                car.setModel(json.getString("model"));
                routingContext.response()
                        .putHeader("content-type", "application/json; charset=utf-8")
                        .end(Json.encodePrettily(car));
            }
        }
    }

}
