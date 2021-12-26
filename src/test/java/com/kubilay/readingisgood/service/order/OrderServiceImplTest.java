package com.kubilay.readingisgood.service.order;

import com.kubilay.readingisgood.entity.order.Order;
import com.kubilay.readingisgood.entity.order.OrderDetail;
import com.kubilay.readingisgood.exception.BusinessException;
import com.kubilay.readingisgood.mapper.order.OrderDetailMapper;
import com.kubilay.readingisgood.mapper.order.OrderMapper;
import com.kubilay.readingisgood.model.dto.OrderDTO;
import com.kubilay.readingisgood.model.dto.OrderDetailDTO;
import com.kubilay.readingisgood.model.request.CreateOrderRequest;
import com.kubilay.readingisgood.model.response.GetOrdersResponse;
import com.kubilay.readingisgood.repository.order.OrderDetailRepository;
import com.kubilay.readingisgood.repository.order.OrderRepository;
import com.kubilay.readingisgood.service.SequenceGeneratorServiceImpl;
import com.kubilay.readingisgood.service.book.impl.BookServiceImpl;
import com.kubilay.readingisgood.service.order.impl.OrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.context.MessageSource;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class OrderServiceImplTest {

    @Mock
    SequenceGeneratorServiceImpl sequenceGenerator;

    @Mock
    MessageSource messageSource;

    @Mock
    OrderRepository orderRepository;

    @Spy
    OrderMapper orderMapper = Mappers.getMapper(OrderMapper.class);

    @Mock
    OrderDetailRepository orderDetailRepository;

    @Spy
    OrderDetailMapper orderDetailMapper = Mappers.getMapper(OrderDetailMapper.class);

    @Mock
    BookServiceImpl bookService;

    @InjectMocks
    OrderServiceImpl orderService;

    @Test
    void test_CreateOrder(){
        Order order = generateOrder();
        OrderDetail orderDetail = generateOrderDetail();
        when(sequenceGenerator.generateSequence(any())).thenReturn(1L);
        when(orderRepository.save(any())).thenReturn(order);
        when(orderDetailRepository.save(any())).thenReturn(orderDetail);
        when(bookService.updateStock(anyLong(), anyInt())).thenReturn(1L);
        assertEquals(1L, orderService.createOrder(generateCreateOrderRequest()));
    }

    @Test
    void test_GetOrdersByCustomerNo_NotNull(){
        Order order = generateOrder();
        when(orderRepository.getOrdersByCustomerNo(any())).thenReturn(List.of(generateOrder()));
        when(orderRepository.getOrderByOrderNo(any())).thenReturn(generateOrder());
        when(orderDetailRepository.getOrderDetailsByOrderNo(any())).thenReturn(List.of(generateOrderDetail()));
        GetOrdersResponse getOrdersResponse = orderService.getOrdersByCustomerNo(1L);
        assertEquals(order.getOrderNo(), getOrdersResponse.getCustomerOrderList().get(0).getOrderDTO().getOrderNo());
    }

    @Test
    void test_GetOrdersByCustomerNo_Null(){
        when(orderRepository.getOrdersByCustomerNo(any())).thenReturn(null);
        assertThrows(BusinessException.class, () -> orderService.getOrdersByCustomerNo(1L));
    }

    @Test
    void test_GetOrderByOrderDateBetweenStartDateAndEndDate_NotNull(){
        Order order = generateOrder();
        when(orderRepository.getOrderByOrderDateBetween(any(), any())).thenReturn(List.of(generateOrder()));
        when(orderRepository.getOrderByOrderNo(any())).thenReturn(generateOrder());
        when(orderDetailRepository.getOrderDetailsByOrderNo(any())).thenReturn(List.of(generateOrderDetail()));
        GetOrdersResponse getOrdersResponse = orderService.getOrderByOrderDateBetweenStartDateAndEndDate(new Date(), new Date());
        assertEquals(order.getOrderNo(), getOrdersResponse.getCustomerOrderList().get(0).getOrderDTO().getOrderNo());
    }

    @Test
    void test_GetOrderByOrderDateBetweenStartDateAndEndDate_Null(){
        when(orderRepository.getOrderByOrderDateBetween(any(), any())).thenReturn(null);
        assertThrows(BusinessException.class, () -> orderService.getOrderByOrderDateBetweenStartDateAndEndDate(new Date(), new Date()));
    }

    @Test
    void test_GetOrderByOrderNo_OrderNull(){
        when(orderRepository.getOrderByOrderNo(any())).thenReturn(null);
        assertThrows(BusinessException.class, () -> orderService.getOrdersByCustomerNo(1L));
    }

    @Test
    void test_GetOrderByOrderNo_OrderDetailNull(){
        when(orderDetailRepository.getOrderDetailsByOrderNo(any())).thenReturn(null);
        assertThrows(BusinessException.class, () -> orderService.getOrdersByCustomerNo(1L));
    }

    @Test
    void test_GetCustomerStatistics_NotNull(){
        Order order = generateOrder();
        when(orderRepository.getOrdersByCustomerNo(any())).thenReturn(List.of(generateOrder()));
        List<OrderDTO> orderDTOList = orderService.getCustomerStatistics(1L);
        assertEquals(order.getOrderNo(), orderDTOList.get(0).getOrderNo());
    }

    @Test
    void test_GetCustomerStatistics_Null(){
        when(orderRepository.getOrdersByCustomerNo(any())).thenReturn(null);
        assertThrows(BusinessException.class, () -> orderService.getCustomerStatistics(1L));
    }

    private OrderDTO generateOrderDTO() {
        return new OrderDTO(null, null, 1L, BigDecimal.TEN, 1, LocalDateTime.now());
    }

    private Order generateOrder() {
        return new Order(1L, 1L, 1L, BigDecimal.TEN, 1, LocalDateTime.now());
    }

    private OrderDetailDTO generateOrderDetailDTO() {
        return new OrderDetailDTO(null, null, 1L, 1, BigDecimal.TEN);
    }

    private OrderDetail generateOrderDetail() {
        return new OrderDetail(1L, 1L, 1L, 1, BigDecimal.TEN);
    }

    private CreateOrderRequest generateCreateOrderRequest() {
        return new CreateOrderRequest(1L, LocalDateTime.now(), List.of(generateOrderDetailDTO()));
    }
}
