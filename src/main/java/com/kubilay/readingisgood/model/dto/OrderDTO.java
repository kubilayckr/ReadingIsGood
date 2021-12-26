package com.kubilay.readingisgood.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    private Long id;

    private Long orderNo;

    @NotNull
    private Long customerNo;

    @NotNull
    private BigDecimal totalPurchaseAmount;

    @NotNull
    private Integer totalBookQuantity;

    @NotNull
    private LocalDateTime orderDate;
}
