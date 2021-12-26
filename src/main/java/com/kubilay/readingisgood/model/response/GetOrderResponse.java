package com.kubilay.readingisgood.model.response;

import com.kubilay.readingisgood.model.dto.OrderDTO;
import com.kubilay.readingisgood.model.dto.OrderDetailDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetOrderResponse {

    private OrderDTO orderDTO;

    private List<OrderDetailDTO> orderDetailList;
}
