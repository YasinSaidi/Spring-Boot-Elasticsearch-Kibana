package com.course.practicaljava.common;

import com.course.practicaljava.repository.CarElasticRepository;
import com.course.practicaljava.rest.domain.Car;
import com.course.practicaljava.rest.service.CarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@Component
public class CarElasticDatasource {

    @Autowired
    private CarElasticRepository carElasticRepository;

    @Autowired
    private CarService randomCarService;

    @Autowired
    private RestTemplate restTemplate;

    private static final Logger log = LoggerFactory.getLogger(CarElasticDatasource.class);

    @EventListener(org.springframework.boot.context.event.ApplicationReadyEvent.class)
    public void populateData() {
        log.info("Start DELETE");
//        var response = restTemplate.exchange("http://localhost:9200/practical-java", HttpMethod.DELETE, null,
//                String.class);
//        log.info("DELETE result : " + response.getBody());

        var cars = new ArrayList<Car>();
        for (int i = 0; i < 10000; i++) {
            cars.add(randomCarService.generateCar());
        }

        carElasticRepository.saveAll(cars);

        log.info("Car in Elasticsearch : " + carElasticRepository.count());
    }

}