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
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document("Order")
public class Order {

    @Transient
    public static final String SEQUENCE_NAME = "order_sequence";

    @Id
    private Long id;

    @NotNull
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
