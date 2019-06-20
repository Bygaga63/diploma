package com.service.antenna.services;

import com.service.antenna.domain.*;
import com.service.antenna.exceptions.CustomException;
import com.service.antenna.repositories.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository repository;
    private final UserService userService;
    private final AddressService addressService;
    private final BreakdownTypeService breakdownTypeService;

    public Set<Task> findAll(User user, boolean isClosed) {
        if (user.getRole() == Role.USER) {
            return repository.findAllByUsersAndIsClosed(user, isClosed);
        }
        return repository.findAll();

    }

    public Set<Task> findAll(Set<Long> users, Set<Long> breakId, Status status, Date start, Date end) {
        Set<User> usersFromdb = userService.findAll(users);
//        User user = userService.findOneRest(userId);

        if (breakId.contains(0L) && status == Status.ALL) {
            return repository.findAllByUsersInAndCreateAtBetween(usersFromdb, start, end);
        }

        if (breakId.contains(0L)) {
            return repository.findAllByUsersInAndStatusAndCreateAtBetween(usersFromdb, status, start, end);
        }

        List<BreakdownType> breakdownTypes = breakdownTypeService.findAllById(breakId);
        if (status == Status.ALL) {
            return repository.findAllByUsersInAndBreakdownTypeAndCreateAtBetween(usersFromdb, breakdownTypes, start, end);
        }

        return repository.findAllByUsersInAndBreakdownTypeAndStatusAndCreateAtBetween(usersFromdb, breakdownTypes, status, start, end);
    }

    public Task findOneRest(User user, Long id) {
        return repository.findByUsersInAndId(user, id).orElseThrow(() -> new CustomException("Заявка не найдена"));
    }

    public Optional<Task> findOne(User user, Long id) {
        return repository.findByUsersInAndId(user, id);
    }

    public boolean remove(Long taskId) {
        repository.deleteById(taskId);
        boolean isExists = repository.existsById(taskId);
        return !isExists;
    }

    public Task create(Task task) {
        Address address = task.getCustomer().getAddress();

        Optional<Address> existAddress = addressService.findAddressRest(address);
        if (existAddress.isPresent()) {
            task.getCustomer().setAddress(address);
        }

        return repository.save(task);
    }

    public Task save(Task task) {
        return repository.save(task);
    }


}
