package com.service.antenna.repositories;

import com.service.antenna.domain.Task;
import com.service.antenna.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {
    Set<Task> findAllByUsers(User user);
    Set<Task> findAll();
    Optional<Task> findByUsersAndId(User user , Long id);
}
