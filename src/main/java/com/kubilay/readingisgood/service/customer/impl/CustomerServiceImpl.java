package com.kubilay.readingisgood.service.customer.impl;

import com.kubilay.readingisgood.entity.customer.Customer;
import com.kubilay.readingisgood.exception.BusinessException;
import com.kubilay.readingisgood.mapper.customer.CustomerMapper;
import com.kubilay.readingisgood.model.dto.CustomerDTO;
import com.kubilay.readingisgood.model.request.CreateUserRequest;
import com.kubilay.readingisgood.repository.customer.CustomerRepository;
import com.kubilay.readingisgood.service.SequenceGeneratorServiceImpl;
import com.kubilay.readingisgood.service.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

import static com.kubilay.readingisgood.exception.BusinessException.ServiceException.CUSTOMER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final SequenceGeneratorServiceImpl sequenceGenerator;
    private final MessageSource messageSource;
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public Long createCustomer(CreateUserRequest request) {
        Customer customer = customerMapper.toModel(request);
        long id = sequenceGenerator.generateSequence(Customer.SEQUENCE_NAME);
        customer.setId(id);
        customer.setCustomerNo(id);
        customerRepository.save(customer);
        return id;
    }

    public CustomerDTO getCustomer(Long customerNo) {
        Customer customer = customerRepository.getCustomerByCustomerNo(customerNo);
        if (customer == null) {
            throw new BusinessException(messageSource.getMessage(CUSTOMER_NOT_FOUND.getKey(), new Object[] {customerNo},
                    Locale.getDefault()) , CUSTOMER_NOT_FOUND.getStatus());
        }
        return customerMapper.toDTO(customer);
    }

    public void updateCustomer(CustomerDTO customerDTO) {
        Customer customer = customerRepository.getCustomerByCustomerNo(customerDTO.getCustomerNo());
        if (customer == null) {
            throw new BusinessException(messageSource.getMessage(CUSTOMER_NOT_FOUND.getKey(), new Object[]{customerDTO.getCustomerNo()},
                    Locale.getDefault()) , CUSTOMER_NOT_FOUND.getStatus());
        } else if (customerDTO.getEmail() != null && !customer.getEmail().equals(customerDTO.getEmail())) {
            throw new BusinessException(messageSource.getMessage(CUSTOMER_NOT_FOUND.getKey(), new Object[]{customerDTO.getCustomerNo()},
                    Locale.getDefault()) , CUSTOMER_NOT_FOUND.getStatus());
        }
        customer.setName(customerDTO.getName());
        customer.setSurname(customerDTO.getSurname());
        customerRepository.save(customer);
    }
}
