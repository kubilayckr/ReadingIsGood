package com.kubilay.readingisgood.service.statistic;

import com.kubilay.readingisgood.model.dto.OrderDTO;
import com.kubilay.readingisgood.model.response.GetCustomerStatisticsResponse;
import com.kubilay.readingisgood.service.order.impl.OrderServiceImpl;
import com.kubilay.readingisgood.service.statistic.impl.StatisticsServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class StatisticsServiceImplTest {

    @Mock
    OrderServiceImpl orderService;

    @InjectMocks
    StatisticsServiceImpl statisticsService;

    @Test
    void test_GetCustomerStatistics(){
        when(orderService.getCustomerStatistics(anyLong())).thenReturn(List.of(generateOrderDTO()));
        GetCustomerStatisticsResponse customerStatistics = statisticsService.getCustomerStatistics(1L);
        assertNotNull(customerStatistics);
    }

    private OrderDTO generateOrderDTO() {
        return new OrderDTO(null, null, 1L, BigDecimal.TEN, 1, LocalDateTime.now());
    }
}
