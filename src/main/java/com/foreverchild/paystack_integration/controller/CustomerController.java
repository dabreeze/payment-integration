package com.foreverchild.paystack_integration.controller;

import com.foreverchild.paystack_integration.model.Customer;
import com.foreverchild.paystack_integration.service.customer.CustomerServiceImpl;
import com.foreverchild.paystack_integration.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    private CustomerServiceImpl customerService;
    @Autowired
    private Utils utils;

    @Autowired
    public void setEmployeeDao(CustomerServiceImpl customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customers")
    public List<Customer> getAllCustomers(){
        return customerService.findAll();
    }

    @GetMapping("/{customerId}")
    public Customer getCustomer(@PathVariable Long customerId){
        Customer employee = customerService.findById(customerId);
        return employee;
    }

    @PostMapping("/customer")
    public Customer saveCustomer(@RequestBody Customer customer){
        Customer employee = customerService.save(customer);
        return employee;
    }

    @PutMapping("/customer")
    public Customer updateEmployee(@RequestBody Customer employeeBody){
        Customer employee = customerService.save(employeeBody);
        return employee;
    }

    @DeleteMapping("/{customerId}")
    public String deleteEmployee(@PathVariable Long customerId){
        String response = customerService.deleteEmployee(customerId);
        return response;
    }

}
