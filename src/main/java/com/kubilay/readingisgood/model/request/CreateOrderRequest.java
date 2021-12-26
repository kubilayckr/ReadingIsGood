package com.kubilay.readingisgood.model.request;

import com.kubilay.readingisgood.model.dto.OrderDetailDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderRequest {

    @NotNull
    private Long customerNo;

    @NotNull
    private LocalDateTime orderDate;

    @NotEmpty
    private List<OrderDetailDTO> orderList;
}
