package com.course.practicaljava.rest.controller;

import com.course.practicaljava.exception.IllegalApiParamException;
import com.course.practicaljava.repository.CarElasticRepository;
import com.course.practicaljava.rest.domain.Car;
import com.course.practicaljava.rest.domain.ErrorResponse;
import com.course.practicaljava.rest.service.CarService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("/api/car/v1")
public class CarRestController {

    @Autowired
    private CarService carService;

    @Autowired
    private CarElasticRepository carElasticRepository;

    private Random random = new Random();

    private Logger log = LoggerFactory.getLogger(CarRestController.class);

    @GetMapping(path = "/random", produces = MediaType.APPLICATION_JSON_VALUE)
    public Car random() {
        return carService.generateCar();
    }

    @PostMapping(path = "/echo", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String echo(@RequestBody Car car) {
        log.info("The car is : " + car);

        return car.toString();
    }

    @GetMapping(path = "/random-cars", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Car> randomCars() {
        var result = new ArrayList<Car>();

        for (int i = 0; i < random.nextInt(6); i++) {
            result.add(carService.generateCar());
        }

        return result;
    }

    @GetMapping(path = "/cars/count")
    public long countCar(){
        return carElasticRepository.count();
    }

    @PostMapping(path = "/cars", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Car createCar(@RequestBody Car car) {
        return carElasticRepository.save(car);
    }

    @GetMapping(path = "/cars/{id}")
    public Car findCarById(@PathVariable String id){
        return carElasticRepository.findById(id).orElse(null);
    }

    @PutMapping(path = "/cars/{id}")
    public Car updateCarById(@PathVariable String id, @RequestBody Car updatedCar){
        updatedCar.setId(id);
        return carElasticRepository.save(updatedCar);
    }

    @GetMapping(path = "/cars/{brand}/{color}")
//    public List<Car> findCarsByPath(@PathVariable String brand, @PathVariable String color,
//                                    @RequestParam(defaultValue = "0") int page,
//                                    @RequestParam(defaultValue = "10") int size) {
    public ResponseEntity<Object> findCarsByPath(@PathVariable String brand, @PathVariable String color,
                                                 @RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "10") int size) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.SERVER, "Spring");
        httpHeaders.add("Custom", "Custom response header");

        if (StringUtils.isNumeric(color)) {
            ErrorResponse errorResponse = new ErrorResponse("Invalid color", System.currentTimeMillis());
//            ResponseEntity<Object> responseEntity = new ResponseEntity<Object>(errorResponse, null, HttpStatus.BAD_REQUEST);
            ResponseEntity<Object> responseEntity = new ResponseEntity<Object>(errorResponse, httpHeaders, HttpStatus.BAD_REQUEST);
            return responseEntity;
        }

        PageRequest pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "price"));
        List<Car> cars = carElasticRepository.findByBrandAndColor(brand, color, pageable).getContent();
        return ResponseEntity.ok().headers(httpHeaders).body(cars);

    }

    @GetMapping(path = "/cars")
    public List<Car> findByParam(@RequestParam String brand, @RequestParam String color,
                                 @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size) {

        if(StringUtils.isNumeric(color)){
            throw new  IllegalArgumentException("Invalid color: " + color);
        }

        if(StringUtils.isNumeric(brand)){
            throw new IllegalApiParamException("Invalid brand: " + brand);
        }

        PageRequest pageable = PageRequest.of(page, size);

        return carElasticRepository.findByBrandAndColor(brand, color, pageable).getContent();

    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleInvalidColorException(IllegalArgumentException e) {
        String errorMessage = "Exception: " + e.getMessage();
        log.warn(errorMessage);

        ErrorResponse errorResponse = new ErrorResponse(errorMessage, System.currentTimeMillis());
        ResponseEntity<ErrorResponse> responseEntity = new ResponseEntity<>(errorResponse, null, HttpStatus.BAD_REQUEST);

        return responseEntity;
    }

    @GetMapping(path = "/cars/date")
    public List<Car> findCarsReleasedAfter(@RequestParam(name = "first_release_date")
                                           @DateTimeFormat(pattern = "yyyy-MM-dd") Date firstReleaseDate) {

        log.info("firstReleaseDate = {}", firstReleaseDate);
        log.info("firstReleaseDate.getTime() = {}", firstReleaseDate.getTime());

        return carElasticRepository.findByFirstReleaseDateAfter(firstReleaseDate.getTime());

    }

    // 1.Handling header: retrieve header by annotation
    @ApiOperation(value = "Get header and display it")
    @GetMapping("/header-one")
    public String headerByAnnotation(@ApiParam(value = "Header User-agent") @RequestHeader(name = "User-agent") String headerUserAgent,
                                     @ApiParam(value = "Header Practical-java") @RequestHeader(name = "Practical-java", required = false) String headerPracticalJava) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("User-agent: " + headerUserAgent);
        stringBuilder.append(", and ");
        stringBuilder.append("Practical-java : " + headerPracticalJava);

        return stringBuilder.toString();

    }

    // 2.Handling header: use HTTPServletRequest class to HTTP header
    @GetMapping("/header-two")
    public String headerByServlet(HttpServletRequest httpServletRequest) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("User-agent: " + httpServletRequest.getHeader("User-agent"));
        stringBuilder.append(", and ");
        stringBuilder.append("Practical-java : " + httpServletRequest.getHeader("Practical-java"));

        return stringBuilder.toString();

    }

    // 3.Handling header: get all headers
    @GetMapping("/header-three")
    public Map<String, String> getAllHeaders(@RequestHeader HttpHeaders httpHeaders) {
        return httpHeaders.toSingleValueMap();
    }

}