package com.course.practicaljava.common;

import com.course.practicaljava.repository.CarPromotionElasticRepository;
import com.course.practicaljava.rest.domain.CarPromotion;
import com.course.practicaljava.rest.service.CarPromotionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CarPromotionDataSource {

    @Autowired
    private CarPromotionElasticRepository carPromotionElasticRepository;

    private final static Logger log = LoggerFactory.getLogger(CarPromotionDataSource.class);

    @EventListener(ApplicationReadyEvent.class)
    public void populateData() {

        carPromotionElasticRepository.deleteAll();

        List<CarPromotion> carPromotions = new ArrayList<CarPromotion>();
        carPromotions.add(new CarPromotion(CarPromotionService.PROMOTION_TYPES.get(0), "Get a free leather seat"));
        carPromotions.add(new CarPromotion(CarPromotionService.PROMOTION_TYPES.get(0), "Update GPS with 3D map"));
        carPromotions
                .add(new CarPromotion(CarPromotionService.PROMOTION_TYPES.get(0), "Connect alarm with your phone"));
        carPromotions.add(new CarPromotion(CarPromotionService.PROMOTION_TYPES.get(1), "Discount 3% if you pay cash"));
        carPromotions.add(new CarPromotion(CarPromotionService.PROMOTION_TYPES.get(1),
                "Discount 3% if this is your second purchase"));
        carPromotions
                .add(new CarPromotion(CarPromotionService.PROMOTION_TYPES.get(1), "Discount 5% if you buy 2 cars"));

        carPromotionElasticRepository.saveAll(carPromotions);

        log.info("Saved all dummy promotion data: " + carPromotionElasticRepository.count());

    }

}