package com.course.practicaljava.rest.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.Objects;

@Document(indexName = "practical-java-2", type = "car-promotion")
public class CarPromotion {

    @Id
    private String id;
    private String type;
    private String description;

    public CarPromotion() {
    }

    public CarPromotion(String type, String description) {
        this.id = id;
        this.type = type;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarPromotion that = (CarPromotion) o;
        return id.equals(that.id) &&
                type.equals(that.type) &&
                description.equals(that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, description);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "CarPromotion{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

}
