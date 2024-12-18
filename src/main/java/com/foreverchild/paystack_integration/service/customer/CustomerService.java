package com.foreverchild.paystack_integration.service.customer;

import com.foreverchild.paystack_integration.model.Customer;

import java.util.List;

public interface CustomerService {
    List<Customer> findAll();

    Customer save(Customer employee);

    Customer findById(Long id);

    String deleteEmployee(Long id);
}
