package com.readingisgood.services;

import com.readingisgood.models.Customer;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface CustomerService {

    Customer createCustomer(Customer customer);

    Optional<Customer> getCustomerById(Long id);

    Page<Customer> getAllCustomer(int pageIndex, int pageSize);
}
