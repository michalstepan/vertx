package com.stepan.dto;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by stepanm on 25.1.2016.
 */
public class Car {

    private static final AtomicInteger counter = new AtomicInteger();
    private int id;
    private String manufacturer;
    private String model;

    public Car() {
        this.id = counter.getAndIncrement();
    }

    public Car(String manufacturer, String model) {
        this.id = counter.getAndIncrement();
        this.manufacturer = manufacturer;
        this.model = model;
    }

    public int getId() {
        return id;
    }


    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
