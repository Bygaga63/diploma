package com.service.antenna.repositories;

import com.service.antenna.domain.Role;
import com.service.antenna.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);
    User getById(Long id);

    Set<User> findAll();
    Set<User> findAllByRole(Role role);
    Set<User> findAllById(Set<Long> id);
}
