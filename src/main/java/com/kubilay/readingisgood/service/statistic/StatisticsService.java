package com.kubilay.readingisgood.service.statistic;

import com.kubilay.readingisgood.model.response.GetCustomerStatisticsResponse;

public interface StatisticsService {

    GetCustomerStatisticsResponse getCustomerStatistics(Long customerNo);
}
