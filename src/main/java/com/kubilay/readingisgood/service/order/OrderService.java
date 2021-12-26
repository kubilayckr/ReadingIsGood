package com.kubilay.readingisgood.service.order;

import com.kubilay.readingisgood.model.dto.OrderDTO;
import com.kubilay.readingisgood.model.request.CreateOrderRequest;
import com.kubilay.readingisgood.model.response.GetOrderResponse;
import com.kubilay.readingisgood.model.response.GetOrdersResponse;

import java.util.Date;
import java.util.List;

public interface OrderService {

    Long createOrder(CreateOrderRequest request);

    GetOrdersResponse getOrdersByCustomerNo(Long customerNo);

    GetOrderResponse getOrderByOrderNo(Long orderNo);

    GetOrdersResponse getOrderByOrderDateBetweenStartDateAndEndDate(Date startDate, Date endDate);

    List<OrderDTO> getCustomerStatistics(Long customerNo);
}
