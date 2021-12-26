package com.kubilay.readingisgood.service.customer;

import com.kubilay.readingisgood.model.dto.CustomerDTO;
import com.kubilay.readingisgood.model.request.CreateUserRequest;

public interface CustomerService {

    Long createCustomer(CreateUserRequest request);

    CustomerDTO getCustomer(Long customerNo);

    void updateCustomer(CustomerDTO customerDTO);
}
