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
    private final BreakdownTypeService breakdownTypeService;
    public Set<Task> findAll(User user, boolean isClosed) {
        if (user.getRole() == Role.USER) {
            return repository.findAllByUsersAndIsClosed(user, isClosed);
        }
        return repository.findAll();

    }

    public Set<Task> findAll(Long userId, List<Long> breakId, Status status, Date date){
        User user = userService.findOneRest(userId);

        if (breakId.contains(0L) && status == Status.ALL) {
            return repository.findAllByUsersAndCreateAtAfter(user, date);
        }

        if (breakId.contains(0L)) {
            return repository.findAllByUsersAndStatusAndCreateAtAfter(user, status, date);
        }

        List<BreakdownType> breakdownTypes = breakdownTypeService.findAllById(breakId);
        if (status == Status.ALL){
            return repository.findAllByUsersAndBreakdownTypeAndCreateAtAfter(user, breakdownTypes, date);
        }

        return repository.findAllByUsersAndBreakdownTypeAndStatusAndCreateAtAfter(user, breakdownTypes, status, date);
    }

    public Task findOneRest(User user, Long id) {
        return repository.findByUsersAndId(user, id).orElseThrow(() -> new CustomException("Заявка не найдена"));
    }

    public Optional<Task>findOne(User user, Long id) {
        return repository.findByUsersAndId(user, id);
    }

    public boolean remove(Long taskId) {
        repository.deleteById(taskId);
        boolean isExists = repository.existsById(taskId);
        return !isExists;
    }

    public Task create(Task task) {
        return repository.save(task);
    }

    public Task save(Task task) {
        return repository.save(task);
    }


}
