package com.kubilay.readingisgood.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailDTO {

    @Null
    private Long id;

    @Null
    private Long orderNo;

    @NotNull
    private Long bookNo;

    @NotNull
    private Integer quantity;

    @NotNull
    private BigDecimal price;
}
