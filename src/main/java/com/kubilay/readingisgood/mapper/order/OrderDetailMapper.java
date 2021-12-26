package com.kubilay.readingisgood.mapper.order;

import com.kubilay.readingisgood.entity.order.OrderDetail;
import com.kubilay.readingisgood.model.dto.OrderDetailDTO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class OrderDetailMapper {

    public abstract OrderDetailDTO toDTO(OrderDetail orderDetail);

    public abstract OrderDetail toModel(OrderDetailDTO orderDetailDTO);

    public abstract List<OrderDetailDTO> toDTO(List<OrderDetail> orderDetailList);

    public abstract List<OrderDetail> toModel(List<OrderDetailDTO> orderDetailDTOList);
}
