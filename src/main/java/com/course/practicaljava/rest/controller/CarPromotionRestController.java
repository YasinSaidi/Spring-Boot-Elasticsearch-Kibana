package com.course.practicaljava.rest.controller;

import com.course.practicaljava.exception.IllegalApiParamException;
import com.course.practicaljava.repository.CarPromotionElasticRepository;
import com.course.practicaljava.rest.domain.CarPromotion;
import com.course.practicaljava.rest.service.CarPromotionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/car/v1")
public class CarPromotionRestController {

    @Autowired
    private CarPromotionService carPromotionService;

    @Autowired
    private CarPromotionElasticRepository carPromotionElasticRepository;

    @GetMapping("/promotions/{promotionType}")
    public List<CarPromotion> listAvailablePromotions(@PathVariable String promotionType,
                                                      @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        if (!carPromotionService.isValidPromotionType(promotionType)) {
            throw new IllegalApiParamException(StringUtils.join("Invalid value for promotion type: ", promotionType,
                    ". ", "Valid values are: ", CarPromotionService.PROMOTION_TYPES));
        }

        var pageable = PageRequest.of(page, size);
        return carPromotionElasticRepository.findByType(promotionType, pageable).getContent();
    }

}
