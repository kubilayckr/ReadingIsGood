package com.kubilay.readingisgood.repository.order;

import com.kubilay.readingisgood.entity.order.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepository extends MongoRepository<Order, Long> {

    Order getOrderByOrderNo(Long orderNo);

    List<Order> getOrdersByCustomerNo(Long customerNo);

    List<Order> getOrderByOrderDateBetween(Date startDate, Date endDate);
}
