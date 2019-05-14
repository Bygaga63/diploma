package com.service.antenna.services;

import com.service.antenna.domain.Address;
import com.service.antenna.exceptions.CustomException;
import com.service.antenna.repositories.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository repository;

    public Optional<Address> findAddressRest(Address address){
        if (address == null ) throw new CustomException("Адрес пустой");
        return repository.findByStreetAndHouseAndAndFlatNumber(address.getStreet(), address.getHouse(), address.getFlatNumber());
    }

    public List<Address> findByWord(String word, Pageable pageable){
       return repository.findByStreetContainingIgnoreCase(word, pageable);
    }
}
