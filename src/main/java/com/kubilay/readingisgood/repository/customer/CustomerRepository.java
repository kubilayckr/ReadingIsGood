package com.kubilay.readingisgood.repository.customer;

import com.kubilay.readingisgood.entity.customer.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, Long> {

    Customer getCustomerByCustomerNo(Long customerNo);
}
