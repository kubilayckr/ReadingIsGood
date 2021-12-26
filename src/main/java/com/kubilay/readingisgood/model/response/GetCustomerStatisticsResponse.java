package com.kubilay.readingisgood.model.response;

import com.kubilay.readingisgood.model.dto.CustomerStatisticsDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Month;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetCustomerStatisticsResponse {

    private Map<Integer, Map<Month, CustomerStatisticsDTO>> customerStatistics;
}
