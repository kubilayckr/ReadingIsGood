package com.kubilay.readingisgood.repository.order;

import com.kubilay.readingisgood.entity.order.OrderDetail;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends MongoRepository<OrderDetail, Long> {

    List<OrderDetail> getOrderDetailsByOrderNo(Long orderNo);
}
