package com.service.antenna.services;

import com.service.antenna.domain.Address;
import com.service.antenna.domain.Task;
import com.service.antenna.exceptions.CustomException;
import com.service.antenna.payload.AddressResponse;
import com.service.antenna.repositories.AddressRepository;
import com.service.antenna.repositories.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository repository;
    private final TaskRepository taskRepository;
    public Optional<Address> findAddressRest(Address address){
        if (address == null ) throw new CustomException("Адрес пустой");
        return repository.findByStreetAndHouseAndAndFlatNumber(address.getStreet(), address.getHouse(), address.getFlatNumber());
    }

    public List<Address> findByWord(String word, Pageable pageable){
       return repository.findByStreetContainingIgnoreCase(word, pageable);
    }

    public List<AddressResponse> getTaskAddress() {
        Set<Task> tasks = taskRepository.findAll();
        return tasks.stream().map(Task::getCustomer).map(customer -> {
            Address address = customer.getAddress();
            return new AddressResponse(address.getId(), address.getStreet(), address.getHouse(), address.getFlatNumber(), customer.getFullName());
        }).collect(Collectors.toSet()).stream().sorted(Comparator.comparing(AddressResponse::getId)).collect(Collectors.toList());
    }
}
