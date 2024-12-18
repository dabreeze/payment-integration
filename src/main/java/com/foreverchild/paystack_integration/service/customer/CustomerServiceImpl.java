package com.foreverchild.paystack_integration.service.customer;

import com.foreverchild.paystack_integration.model.Customer;
import com.foreverchild.paystack_integration.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService{
    private CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public Customer save(Customer customer) {
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
