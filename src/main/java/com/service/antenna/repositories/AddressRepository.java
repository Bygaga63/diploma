package com.service.antenna.repositories;

import com.service.antenna.domain.Address;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends PagingAndSortingRepository<Address, Long> {
    List<Address> findByStreetContainingIgnoreCase(String word, Pageable pageable);

    Optional<Address> findByStreetAndHouseAndAndFlatNumber(String street, String house, String number);
}
