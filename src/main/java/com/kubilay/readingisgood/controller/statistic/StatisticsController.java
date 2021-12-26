package com.kubilay.readingisgood.controller.statistic;

import com.kubilay.readingisgood.model.response.GetCustomerStatisticsResponse;
import com.kubilay.readingisgood.service.statistic.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/api/v1/statistics")
@RequiredArgsConstructor
@Validated
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping("/statistic/{customerNo}")
    public ResponseEntity<GetCustomerStatisticsResponse> getCustomerStatistic(@Positive @PathVariable(value = "customerNo")  Long customerNo) {
        return new ResponseEntity<>(statisticsService.getCustomerStatistics(customerNo), HttpStatus.OK);
    }
}
