package com.kubilay.readingisgood.controller.order;

import com.kubilay.readingisgood.exception.BusinessException;
import com.kubilay.readingisgood.model.request.CreateOrderRequest;
import com.kubilay.readingisgood.model.response.GetOrderResponse;
import com.kubilay.readingisgood.model.response.GetOrdersResponse;
import com.kubilay.readingisgood.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.Date;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Validated
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/order")
    public ResponseEntity<Long> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        return new ResponseEntity<>(orderService.createOrder(request), HttpStatus.CREATED);
    }

    @GetMapping("/order/{orderNo}")
    public ResponseEntity<GetOrderResponse> getOrderByOrderNo(@Positive @PathVariable("orderNo") Long orderNo) {
        return new ResponseEntity<>(orderService.getOrderByOrderNo(orderNo), HttpStatus.OK);
    }

    @GetMapping("/customerOrder/{customerNo}")
    public ResponseEntity<GetOrdersResponse> getOrdersByCustomerNo(@Positive @PathVariable("customerNo") Long customerNo) {
        return new ResponseEntity<>(orderService.getOrdersByCustomerNo(customerNo), HttpStatus.OK);
    }

    @GetMapping("/customerOrder")
    public ResponseEntity<GetOrdersResponse> getOrderByOrderDateBetweenStartDateAndEndDate(
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date startDate,
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date endDate) {
        return new ResponseEntity<>(orderService.getOrderByOrderDateBetweenStartDateAndEndDate(startDate, endDate), HttpStatus.OK);
    }
}
