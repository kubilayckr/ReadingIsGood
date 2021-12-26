package com.kubilay.readingisgood.mapper.customer;

import com.kubilay.readingisgood.entity.customer.Customer;
import com.kubilay.readingisgood.model.dto.CustomerDTO;
import com.kubilay.readingisgood.model.request.CreateUserRequest;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class CustomerMapper {

    public abstract CustomerDTO toDTO(Customer customer);

    public abstract Customer toModel(CustomerDTO customerDTO);

    public abstract Customer toModel(CreateUserRequest createUserRequest);
}
