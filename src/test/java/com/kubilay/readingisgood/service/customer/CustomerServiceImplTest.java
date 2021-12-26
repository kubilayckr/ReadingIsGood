package com.kubilay.readingisgood.service.customer;

import com.kubilay.readingisgood.entity.customer.Customer;
import com.kubilay.readingisgood.exception.BusinessException;
import com.kubilay.readingisgood.mapper.customer.CustomerMapper;
import com.kubilay.readingisgood.model.dto.CustomerDTO;
import com.kubilay.readingisgood.model.request.CreateUserRequest;
import com.kubilay.readingisgood.repository.customer.CustomerRepository;
import com.kubilay.readingisgood.service.SequenceGeneratorServiceImpl;
import com.kubilay.readingisgood.service.customer.impl.CustomerServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceImplTest {

    @Mock
    SequenceGeneratorServiceImpl sequenceGenerator;

    @Mock
    MessageSource messageSource;

    @Mock
    CustomerRepository customerRepository;

    @Spy
    CustomerMapper customerMapper = Mappers.getMapper(CustomerMapper.class);

    @InjectMocks
    CustomerServiceImpl customerService;

    @Test
    void test_CreateCustomer(){
        CreateUserRequest request = generateCreateUserRequest();
        Customer customer = generateCustomer();
        when(sequenceGenerator.generateSequence(any())).thenReturn(1L);
        when(customerRepository.save(any())).thenReturn(customer);
        assertEquals(1L, customerService.createCustomer(request));
    }

    @Test
    void test_GetCustomer_NotNull(){
        Customer customer = generateCustomer();
        when(customerRepository.getCustomerByCustomerNo(any())).thenReturn(customer);
        CustomerDTO customerDTO = customerService.getCustomer(1L);
        assertEquals(customer.getCustomerNo(), customerDTO.getCustomerNo());
    }

    @Test
    void test_GetCustomer_Null(){
        when(customerRepository.getCustomerByCustomerNo(any())).thenReturn(null);
        assertThrows(BusinessException.class, () -> customerService.getCustomer(1L));
    }

    @Test
    void test_UpdateCustomer_DifferentEmail(){
        Customer customer = generateCustomer();
        when(customerRepository.getCustomerByCustomerNo(any())).thenReturn(customer);
        assertThrows(BusinessException.class, () -> customerService.updateCustomer(new CustomerDTO(null, null, "Name", "Surname", "email2@gmail.com")));
    }

    @Test
    void test_UpdateCustomer_Null(){
        when(customerRepository.getCustomerByCustomerNo(any())).thenReturn(null);
        assertThrows(BusinessException.class, () -> customerService.updateCustomer(generateCustomerDTO()));
    }

    private CustomerDTO generateCustomerDTO() {
        return new CustomerDTO(null, null, "Name", "Surname", "email@gmail.com");
    }

    private Customer generateCustomer() {
        return new Customer(1L, 1L, "Name", "Surname", "email@gmail.com");
    }

    private CreateUserRequest generateCreateUserRequest() {
        return new CreateUserRequest("Name", "Surname", "email@gmail.com", null, null);
    }
}
