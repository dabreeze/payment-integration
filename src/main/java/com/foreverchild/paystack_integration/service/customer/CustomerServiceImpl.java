package com.foreverchild.paystack_integration.service.customer;

import com.foreverchild.paystack_integration.model.Customer;
import com.foreverchild.paystack_integration.repository.CustomerRepository;
import com.foreverchild.paystack_integration.utils.Utils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService{
    private CustomerRepository customerRepository;
    private Utils utils;

    public CustomerServiceImpl(CustomerRepository customerRepository, Utils utils) {
        this.customerRepository = customerRepository;
        this.utils = utils;
    }

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public Customer save(Customer customer) {
//        Customer customer =  utils.jsonToObject(saveCustomer,Customer.class);
        return customerRepository.save(customer);
    }

    @Override
    public Customer findById(Long id) {
        Optional<Customer> employee = customerRepository.findById(id);
        if(employee.isEmpty()){
            throw new NullPointerException("Cannot find any employee with id:"+id);
        }
        return employee.get();
    }

    @Override
    public String deleteEmployee(Long id) {
        Optional<Customer> employee = customerRepository.findById(id);
        if(employee.isEmpty()){
            throw new NullPointerException("Cannot find any employee with id:"+id);
        }
        customerRepository.deleteById(id);
        return "Employee deleted";
    }
}
