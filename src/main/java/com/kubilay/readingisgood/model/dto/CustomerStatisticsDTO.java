package com.kubilay.readingisgood.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerStatisticsDTO {

    private Integer totalOrderCount;

    private Integer totalBookCount;

    private BigDecimal totalPurchasedAmount;
}
