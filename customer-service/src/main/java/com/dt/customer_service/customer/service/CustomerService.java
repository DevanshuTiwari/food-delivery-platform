package com.dt.customer_service.customer.service;

import com.dt.customer_service.customer.dto.CustomerRegistrationRequest;
import com.dt.customer_service.customer.dto.CustomerResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerService {
    void registerCustomer(CustomerRegistrationRequest request);

    List<CustomerResponse> getAllCustomers();

    CustomerResponse getCustomerById(Long id);
}
