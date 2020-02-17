package com.course.practicaljava.rest.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;
import java.util.List;

@Document(indexName = "practical-java", type = "car")
public class Car {

    private String brand;
    private String color;
    private String type;
    private int price;
    private boolean available;

    @Id
    private String id;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", timezone = "Asia/Jakarta")
    @Field(type = FieldType.Date, format = DateFormat.date_time)
    private Date firstReleaseDate;

    @JsonInclude(value = Include.NON_EMPTY)
    private List<String> additionalFeatures;

    @JsonUnwrapped
    private Engine engine;

    private List<Tire> compatibleTires;

    @JsonInclude(value = Include.NON_EMPTY)
    private String secretFeature;

    public Car() {

    }

    public Car(String brand, String color, String type) {
        super();
        this.brand = brand;
        this.color = color;
        this.type = type;
    }

    public List<String> getAdditionalFeatures() {
        return additionalFeatures;
    }

    public String getBrand() {
        return brand;
    }

    public String getColor() {
        return color;
    }

    public List<Tire> getCompatibleTires() {
        return compatibleTires;
    }

    public Engine getEngine() {
        return engine;
    }

    public Date getFirstReleaseDate() {
        return firstReleaseDate;
    }

    public String getId() {
        return id;
    }

    public int getPrice() {
        return price;
    }

    public String getSecretFeature() {
        return secretFeature;
    }

    public String getType() {
        return type;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAdditionalFeatures(List<String> additionalFeatures) {
        this.additionalFeatures = additionalFeatures;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setCompatibleTires(List<Tire> compatibleTires) {
        this.compatibleTires = compatibleTires;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public void setFirstReleaseDate(Date firstReleaseDate) {
        this.firstReleaseDate = firstReleaseDate;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setSecretFeature(String secretFeature) {
        this.secretFeature = secretFeature;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Car [brand=" + brand + ", color=" + color + ", type=" + type + ", price=" + price + ", available="
                + available + ", firstReleaseDate=" + firstReleaseDate + ", additionalFeatures=" + additionalFeatures
                + ", engine=" + engine + ", compatibleTires=" + compatibleTires + ", secretFeature=" + secretFeature
                + "]";
    }

}