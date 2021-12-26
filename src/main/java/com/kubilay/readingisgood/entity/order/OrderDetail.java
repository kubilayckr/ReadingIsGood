package com.kubilay.readingisgood.entity.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document("OrderDetail")
public class OrderDetail {

    @Transient
    public static final String SEQUENCE_NAME = "order_detail_sequence";

    @Id
    private Long id;

    @NotNull
    private Long orderNo;

    @NotNull
    private Long bookNo;

    @NotNull
    private Integer quantity;

    @NotNull
    private BigDecimal price;
}
