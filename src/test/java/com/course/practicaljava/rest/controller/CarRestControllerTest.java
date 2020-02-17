package com.course.practicaljava.rest.controller;

import com.course.practicaljava.rest.domain.Car;
import com.course.practicaljava.rest.service.CarService;
import com.course.practicaljava.rest.service.RandomCarService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import org.springframework.http.HttpHeaders;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertTrue;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CarRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RandomCarService randomCarService;

    private ObjectMapper objectMapper = new ObjectMapper();

    private Logger log = LoggerFactory.getLogger(CarRestControllerTest.class);

    @Test
    void random() {
        String endpoint = "/api/car/v1/random";

        for (int i = 0; i < 100; i++) {
            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(endpoint);

            MvcResult mockResult = null;
            try {
                mockResult = mockMvc.perform(requestBuilder).andReturn();
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                String content = mockResult.getResponse().getContentAsString();
                log.info("content = {}", content);

                Car car = objectMapper.readValue(content, Car.class);
                log.info("car = {}", car.toString());

                assertTrue(CarService.BRANDS.contains(car.getBrand()) && CarService.COLORS.contains(car.getColor()));

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException f) {
                f.printStackTrace();
            }
        }
    }

//    @Test
    void echo() {
    }

//    @Test
    void randomCars() {
    }

//    @Test
    void countCar() {
    }

    @Test
    void createCar() {

        for (int i = 0; i < 100; i++) {
            Car car = randomCarService.generateCar();
            try {
                String carToString = objectMapper.writeValueAsString(car);

                String endpoint = "/api/car/v1/cars";
                MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(endpoint)
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(carToString);

                assertTimeout(Duration.ofSeconds(1), () -> {
                    mockMvc.perform(requestBuilder).andExpect(status().is2xxSuccessful());
                });

            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

    }

//    @Test
    void findCarById() {
    }

//    @Test
    void updateCarById() {
    }

    @Test
    void findCarsByPath() {

        int pageSize = 5;

        for (String brand : CarService.BRANDS) {
            for (String color : CarService.COLORS) {
                String endpoint = StringUtils.join("/api/car/v1/cars/", brand, "/", color);
                MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(endpoint)
                        .param("size", Integer.toString(pageSize))
                        .param("page", Integer.toString(1))
                        .param("brand", brand)
                        .param("color", color);

                MvcResult mockResult = null;
                try {
                    mockResult = mockMvc.perform(requestBuilder).andReturn();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                String responseBody = null;
                try {
                    responseBody = mockResult.getResponse().getContentAsString();
                    log.info("content = {}", responseBody);
                    List<Car> listCars = objectMapper.readValue(responseBody, new TypeReference<List<Car>>() {
                    });

                    assertNotNull(listCars);
                    assertTrue(listCars.size() >= 0 && listCars.size() <= pageSize);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

//    @Test
    void findByParam() {
    }

//    @Test
    void findCarsReleasedAfter() {
    }

    @Test
    void headerByAnnotation() {
        String endpoint = "/api/car/v1/header-one";

        String headerOne = "MockMvc";
        String headerTwo = "MyJava";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("User-agent", headerOne);
        httpHeaders.add("Practical-java", headerTwo);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(endpoint).headers(httpHeaders);

        try {

            mockMvc.perform(requestBuilder).andExpect(content().string(containsString(headerOne))).andExpect(content()
                    .string(containsString(headerTwo)));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    @Test
    void headerByServlet() {
    }

//    @Test
    void getAllHeaders() {
    }
}