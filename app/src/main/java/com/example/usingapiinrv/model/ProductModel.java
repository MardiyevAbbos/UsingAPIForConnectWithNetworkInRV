package com.example.usingapiinrv.model;

import java.util.Date;

public class ProductModel {

    private long id;
    private String name;
    private double price;
    private String createdDate;
    private String unit;

    public ProductModel(long id, String name, double price, String createdDate, String unit) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.createdDate = createdDate;
        this.unit = unit;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
