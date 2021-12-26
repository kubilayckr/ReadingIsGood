package com.kubilay.readingisgood.service.statistic.impl;

import com.kubilay.readingisgood.model.dto.CustomerStatisticsDTO;
import com.kubilay.readingisgood.model.dto.OrderDTO;
import com.kubilay.readingisgood.model.response.GetCustomerStatisticsResponse;
import com.kubilay.readingisgood.service.order.OrderService;
import com.kubilay.readingisgood.service.statistic.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.groupingBy;

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

    private final OrderService orderService;

    @Override
    public GetCustomerStatisticsResponse getCustomerStatistics(Long customerNo) {
        List<OrderDTO> customerStatistics = orderService.getCustomerStatistics(customerNo);
        return new GetCustomerStatisticsResponse(customerStatistics.stream().collect(
                groupingBy(x -> x.getOrderDate().getYear(),
                    groupingBy(x -> x.getOrderDate().getMonth(),
                        collectingAndThen(Collectors.toList(),
                            list -> {
                                Integer totalOrderCount = list.size();
                                Integer totalBookCount = list.stream().map(OrderDTO::getTotalBookQuantity).reduce(0, Integer::sum);
                                BigDecimal totalPurchasedAmount = list.stream().map(OrderDTO::getTotalPurchaseAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
                                return new CustomerStatisticsDTO(totalOrderCount, totalBookCount, totalPurchasedAmount);
                            }
                )))));
    }
}
