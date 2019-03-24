package com.service.antenna.services;

import com.service.antenna.domain.Customer;
import com.service.antenna.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository repository;

    public List<Customer> findAll(){
        return repository.findAll();
    }

    public Customer save(Customer customer){
        return repository.save(customer);
    }
}
