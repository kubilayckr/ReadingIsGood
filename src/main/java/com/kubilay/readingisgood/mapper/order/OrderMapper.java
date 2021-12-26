package com.kubilay.readingisgood.mapper.order;

import com.kubilay.readingisgood.entity.order.Order;
import com.kubilay.readingisgood.model.dto.OrderDTO;
import com.kubilay.readingisgood.model.request.CreateOrderRequest;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class OrderMapper {

    public abstract OrderDTO toDTO(Order order);

    public abstract Order toModel(OrderDTO orderDTO);

    public abstract Order toModel(CreateOrderRequest request);

    public abstract List<OrderDTO> toDTO(List<Order> order);

    public abstract List<Order> toModel(List<OrderDTO> orderDTO);
}
