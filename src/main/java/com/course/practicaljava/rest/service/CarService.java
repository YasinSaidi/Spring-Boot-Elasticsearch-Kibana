package com.course.practicaljava.rest.service;

import com.course.practicaljava.rest.domain.Car;

import java.util.List;

public interface CarService {

    List<String> BRANDS = List.of("Toyota", "Honda", "Ford", "Mitsubishi", "Chevrolet");
    List<String> COLORS = List.of("Red", "Black", "White", "Blue", "Silver");
    List<String> TYPES = List.of("Sedan", "SUV", "MPV", "Truck", "Coupe");
    List<String> ADDITIONAL_FEATURES = List.of("GPS", "Alarm", "Sunroof", "Media player", "Leather seats");
    List<String> FUEL_TYPES = List.of("Petrol", "Electric", "Hybrid");
    List<String> TIRE_MANUFACTURERS = List.of("Goodyear", "Bridgestone", "Dunlop");

    Car generateCar();

}