package com.kubilay.readingisgood.controller.customer;

import com.kubilay.readingisgood.model.dto.CustomerDTO;
import com.kubilay.readingisgood.service.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
@Validated
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/customer/{customerNo}")
    public ResponseEntity<CustomerDTO> getCustomer(@Positive @PathVariable("customerNo") Long customerNo) {
        return new ResponseEntity<>(customerService.getCustomer(customerNo), HttpStatus.OK);
    }

    @PutMapping("/customer")
    public ResponseEntity<Object> updateCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
        customerService.updateCustomer(customerDTO);
        return new ResponseEntity<>((Object) null, HttpStatus.OK);
    }
}
