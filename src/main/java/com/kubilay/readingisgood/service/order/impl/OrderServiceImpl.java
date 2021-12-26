package com.kubilay.readingisgood.service.order.impl;

import com.kubilay.readingisgood.entity.order.Order;
import com.kubilay.readingisgood.entity.order.OrderDetail;
import com.kubilay.readingisgood.exception.BusinessException;
import com.kubilay.readingisgood.mapper.order.OrderDetailMapper;
import com.kubilay.readingisgood.mapper.order.OrderMapper;
import com.kubilay.readingisgood.model.dto.OrderDTO;
import com.kubilay.readingisgood.model.dto.OrderDetailDTO;
import com.kubilay.readingisgood.model.request.CreateOrderRequest;
import com.kubilay.readingisgood.model.response.GetOrderResponse;
import com.kubilay.readingisgood.model.response.GetOrdersResponse;
import com.kubilay.readingisgood.repository.order.OrderDetailRepository;
import com.kubilay.readingisgood.repository.order.OrderRepository;
import com.kubilay.readingisgood.service.SequenceGeneratorServiceImpl;
import com.kubilay.readingisgood.service.book.BookService;
import com.kubilay.readingisgood.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.kubilay.readingisgood.exception.BusinessException.ServiceException.*;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final SequenceGeneratorServiceImpl sequenceGenerator;
    private final MessageSource messageSource;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderDetailRepository orderDetailRepository;
    private final OrderDetailMapper orderDetailMapper;
    private final BookService bookService;

    public Long createOrder(CreateOrderRequest request) {
        Order order = orderMapper.toModel(request);
        long id = sequenceGenerator.generateSequence(Order.SEQUENCE_NAME);
        order.setId(id);
        order.setOrderNo(id);
        order.setTotalBookQuantity(calculateTotalBookQuantity(request.getOrderList()));
        order.setTotalPurchaseAmount(calculateTotalPurchaseAmount(request.getOrderList()));
        orderRepository.save(order);

        List<OrderDetail> orderDetails = orderDetailMapper.toModel(request.getOrderList());
        orderDetails.forEach(o -> {
            bookService.updateStock(o.getBookNo(), -o.getQuantity());
            o.setId(sequenceGenerator.generateSequence(OrderDetail.SEQUENCE_NAME));
            o.setOrderNo(id);
        });
        orderDetailRepository.saveAll(orderDetails);
        return id;
    }

    public GetOrdersResponse getOrdersByCustomerNo(Long customerNo) {
        List<Order> orderList = orderRepository.getOrdersByCustomerNo(customerNo);
        if (CollectionUtils.isEmpty(orderList)) {
            throw new BusinessException(messageSource.getMessage(ORDER_NOT_FOUND.getKey(), new Object[]{customerNo},
                    Locale.getDefault()), ORDER_NOT_FOUND.getStatus());
        }
        return getGetOrdersDetailsResponse(orderList);
    }

    public GetOrdersResponse getOrderByOrderDateBetweenStartDateAndEndDate(Date startDate, Date endDate) {
        if(0 < startDate.compareTo(endDate)) {
            throw new BusinessException(messageSource.getMessage(ORDER_START_DATE_GREATER_THAN_END_DATE.getKey(), new Object[]{startDate + " - " + endDate},
                    Locale.getDefault()), ORDER_START_DATE_GREATER_THAN_END_DATE.getStatus());
        }
        List<Order> orderList = orderRepository.getOrderByOrderDateBetween(startDate, endDate);
        if (CollectionUtils.isEmpty(orderList)) {
            throw new BusinessException(messageSource.getMessage(ORDER_NOT_FOUND.getKey(), new Object[]{startDate + " - " + endDate},
                    Locale.getDefault()), ORDER_NOT_FOUND.getStatus());
        }
        return getGetOrdersDetailsResponse(orderList);
    }

    public GetOrderResponse getOrderByOrderNo(Long orderNo) {
        Order order = orderRepository.getOrderByOrderNo(orderNo);
        if (order == null) {
            throw new BusinessException(messageSource.getMessage(ORDER_NOT_FOUND.getKey(), new Object[]{orderNo},
                    Locale.getDefault()), ORDER_NOT_FOUND.getStatus());
        }
        OrderDTO orderDTO = orderMapper.toDTO(order);

        List<OrderDetail> orderDetail = orderDetailRepository.getOrderDetailsByOrderNo(orderNo);
        if (CollectionUtils.isEmpty(orderDetail)) {
            throw new BusinessException(messageSource.getMessage(ORDER_DETAIL_NOT_FOUND.getKey(), new Object[]{orderNo},
                    Locale.getDefault()), ORDER_DETAIL_NOT_FOUND.getStatus());
        }

        List<OrderDetailDTO> orderDetailDTOList = orderDetailMapper.toDTO(orderDetail);

        return new GetOrderResponse(orderDTO, orderDetailDTOList);
    }

    public List<OrderDTO> getCustomerStatistics(Long customerNo) {
        List<Order> orderList = orderRepository.getOrdersByCustomerNo(customerNo);
        if (CollectionUtils.isEmpty(orderList)) {
            throw new BusinessException(messageSource.getMessage(ORDER_NOT_FOUND.getKey(), new Object[]{customerNo},
                    Locale.getDefault()), ORDER_NOT_FOUND.getStatus());
        }
        return orderMapper.toDTO(orderList);
    }

    private GetOrdersResponse getGetOrdersDetailsResponse(List<Order> orderList) {
        List<OrderDTO> orderDTOList = orderMapper.toDTO(orderList);

        List<GetOrderResponse> customerOrderList = new ArrayList<>();
        for (OrderDTO orderDTO : orderDTOList) {
            customerOrderList.add(getOrderByOrderNo(orderDTO.getOrderNo()));
        }
        return new GetOrdersResponse(customerOrderList);
    }

    private int calculateTotalBookQuantity(List<OrderDetailDTO> orderList) {
        return orderList.stream().map(o -> o.getQuantity()).reduce(0, Integer::sum);
    }

    private BigDecimal calculateTotalPurchaseAmount(List<OrderDetailDTO> orderList) {
        return orderList.stream().map(o -> o.getPrice().multiply(BigDecimal.valueOf(o.getQuantity()))).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
