package com.service.antenna.services;

import com.service.antenna.domain.Address;
import com.service.antenna.repositories.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository repository;

    public List<Address> findByWord(String word, Pageable pageable){
       return repository.findByStreetContainingIgnoreCase(word, pageable);
    }
}
