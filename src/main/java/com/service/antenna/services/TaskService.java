package com.service.antenna.services;

import com.service.antenna.domain.Role;
import com.service.antenna.domain.Task;
import com.service.antenna.domain.User;
import com.service.antenna.exceptions.CustomException;
import com.service.antenna.repositories.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository repository;

    public Set<Task> findAll(User user) {
        if (user.getRole() == Role.USER) {
            return repository.findAllByUser(user);
        }
        return repository.findAll();

    }

    public Task findOneRest(User user, Long id) {
        return repository.findByUserAndId(user, id).orElseThrow(() -> new CustomException("Заявка не найдена"));
    }

    public Optional<Task>findOne(User user, Long id) {
        return repository.findById(id);
    }

    public boolean remove(Long taskId) {
        repository.deleteById(taskId);
        boolean isExists = repository.existsById(taskId);
        return !isExists;
    }

    public Task create(User user, Task task) {
        task.getUser().add(user);
        return repository.save(task);
    }

    public Task save(Task task) {
        return repository.save(task);
    }


}
