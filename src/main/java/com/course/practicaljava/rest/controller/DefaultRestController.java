package com.course.practicaljava.rest.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;
import java.util.Random;

@RestController
@RequestMapping("/api")
@Api(tags = {"Default API"}, description = "API from class DefaultRestController")
public class DefaultRestController {

    private Logger log = LoggerFactory.getLogger(DefaultRestController.class);

    @ApiOperation("Welcome to Spring Boot")
    @GetMapping("/welcome")
    public String welcome() {
        log.info(StringUtils.join("Hello ", "This is ", "Spring Boot ", "REST API"));

        return "Welcome to Spring Boot";
    }

    @GetMapping("/time")
    public String time() {
        return LocalTime.now().toString();
    }

    @GetMapping("/random-error")
    public ResponseEntity<String> randomError() {

        int remainder = new Random().nextInt() % 6;

        String body = "kibana";

        switch (remainder) {
            case 0:
                return ResponseEntity.ok().body(body);
            case 1:
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
            case 2:
                return ResponseEntity.badRequest().body(body);
            default:
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);

        }

    }

}