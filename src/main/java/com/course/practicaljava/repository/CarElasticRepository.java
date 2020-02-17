package com.course.practicaljava.repository;

import com.course.practicaljava.rest.domain.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarElasticRepository extends ElasticsearchRepository<Car, String> {

//    List<Car> findByBrandAndColor(String brand, String color);
    Page<Car> findByBrandAndColor(String brand, String color, Pageable pageable);

    //date represented in milliseconds
    @Query("{ \"range\":{ \"first_release_date\":{ \"gt\":?0 } } }")
    List<Car> findByFirstReleaseDateAfter(long date);

}